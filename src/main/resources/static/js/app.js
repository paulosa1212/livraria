const API = '';
let estado = {
    autores: [],
    autoresFiltrados: [],
    editandoAutor: null,
    editandoLivro: null,
};

/* ========== Utilidades ========== */
function mostrarToast(mensagem) {
    const t = document.getElementById('toast');
    t.textContent = mensagem;
    t.classList.add('show');
    setTimeout(() => t.classList.remove('show'), 2500);
}

function formatarData(iso) {
    if (!iso) return '-';
    const d = new Date(iso);
    if (isNaN(d.getTime())) return iso;
    return d.toLocaleDateString('pt-BR');
}

/* Mensagem de validação vinda do backend (campo + mensagem) */
function extrairErros(responseJson) {
    if (responseJson && responseJson.erros) {
        return responseJson.erros.map(e => `${e.campo}: ${e.mensagem}`).join('\n');
    }
    if (responseJson && responseJson.mensagem) {
        return responseJson.mensagem;
    }
    return null;
}

/* ========== Autores ========== */
async function carregarAutores() {
    try {
        const res = await fetch(`${API}/autores`);
        if (!res.ok) throw new Error('Erro ao carregar autores');
        const autores = await res.json();
        estado.autores = autores;
        estado.autoresFiltrados = autores;
        renderAutores();
    } catch (e) {
        mostrarToast(e.message);
    }
}

function renderAutores() {
    const tbody = document.getElementById('tabela-autores-body');
    if (!estado.autoresFiltrados.length) {
        tbody.innerHTML = '<tr><td colspan="4" class="empty">Nenhum autor encontrado</td></tr>';
        return;
    }
    tbody.innerHTML = estado.autoresFiltrados.map(a => `
        <tr>
            <td>${a.nome}</td>
            <td>${formatarData(a.dataNascimento)}</td>
            <td>${a.nacionalidade}</td>
            <td>
                <button class="btn btn-small btn-danger" onclick="editarAutor('${a.id}')">Editar</button>
                <button class="btn btn-small btn-danger" onclick="deletarAutor('${a.id}')">Excluir</button>
            </td>
        </tr>`).join('');
}

async function salvarAutor(event) {
    event.preventDefault();
    const autorDTO = {
        nome: document.getElementById('autor-nome').value.trim(),
        dataNascimento: document.getElementById('autor-data').value,
        nacionalidade: document.getElementById('autor-nacionalidade').value.trim(),
    };

    try {
        let res;
        if (estado.editandoAutor) {
            res = await fetch(`${API}/autores/${estado.editandoAutor}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(autorDTO),
            });
        } else {
            res = await fetch(`${API}/autores`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(autorDTO),
            });
        }

        if (res.status === 201 || res.status === 204) {
            mostrarToast(estado.editandoAutor ? 'Autor atualizado com sucesso' : 'Autor cadastrado com sucesso');
            estado.editandoAutor = null;
            document.getElementById('btn-cancelar-autor').style.display = 'none';
            cancelarAutor();
            carregarAutores();
        } else {
            const json = await res.json().catch(() => null);
            const erro = extrairErros(json) || `Erro (${res.status})`;
            mostrarToast(erro);
        }
    } catch (e) {
        mostrarToast(e.message);
    }
}

function editarAutor(id) {
    const autor = estado.autores.find(a => a.id === id);
    if (!autor) return;
    estado.editandoAutor = id;
    document.getElementById('autor-nome').value = autor.nome;
    document.getElementById('autor-data').value = autor.dataNascimento ? autor.dataNascimento.split('T')[0] : '';
    document.getElementById('autor-nacionalidade').value = autor.nacionalidade;
    document.getElementById('btn-cancelar-autor').style.display = 'inline-block';
}

async function deletarAutor(id) {
    if (!confirm('Excluir este autor?')) return;
    try {
        const res = await fetch(`${API}/autores/${id}`, { method: 'DELETE' });
        if (res.status === 204) {
            mostrarToast('Autor excluído');
            carregarAutores();
        } else {
            const json = await res.json().catch(() => null);
            mostrarToast(extrairErros(json) || `Erro (${res.status})`);
        }
    } catch (e) {
        mostrarToast(e.message);
    }
}

function cancelarAutor() {
    estado.editandoAutor = null;
    document.getElementById('autor-form').reset();
    document.getElementById('btn-cancelar-autor').style.display = 'none';
}

function filtrarAutores() {
    const termo = document.getElementById('busca-autor').value.toLowerCase();
    estado.autoresFiltrados = estado.autores.filter(a =>
        !termo || a.nome.toLowerCase().includes(termo) || a.nacionalidade.toLowerCase().includes(termo)
    );
    renderAutores();
}

/* ========== Livros ========== */
async function carregarLivros() {
    try {
        const res = await fetch(`${API}/livros`);
        if (!res.ok) throw new Error('Erro ao carregar livros');
        const livros = await res.json();
        estado.livros = livros;
        renderLivros();
    } catch (e) {
        mostrarToast(e.message);
    }
}

async function carregarAutoresParaLivro() {
    try {
        const res = await fetch(`${API}/autores`);
        const autores = await res.json();
        const sel = document.getElementById('livro-autor');
        sel.innerHTML = '<option value="">Selecione um autor</option>' +
            autores.map(a => `<option value="${a.id}">${a.nome}</option>`).join('');
    } catch (e) {
        console.error(e);
    }
}

function renderLivros() {
    const tbody = document.getElementById('tabela-livros-body');
    if (!estado.livros || !estado.livros.length) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">Nenhum livro encontrado</td></tr>';
        return;
    }
    tbody.innerHTML = estado.livros.map(l => `
        <tr>
            <td>${l.titulo}</td>
            <td>${l.isbn || '-'}</td>
            <td>${formatarData(l.dataPublicacao)}</td>
            <td>${l.autorNome || '-'}</td>
            <td>
                <button class="btn btn-small btn-danger" onclick="editarLivro('${l.id}')">Editar</button>
                <button class="btn btn-small btn-danger" onclick="deletarLivro('${l.id}')">Excluir</button>
            </td>
        </tr>`).join('');
}

async function salvarLivro(event) {
    event.preventDefault();
    const dto = {
        titulo: document.getElementById('livro-titulo').value.trim(),
        dataPublicacao: document.getElementById('livro-data').value,
        idAutor: document.getElementById('livro-autor').value,
        isbn: document.getElementById('livro-isbn').value.trim(),
    };
    try {
        let res;
        if (estado.editandoLivro) {
            res = await fetch(`${API}/livros/${estado.editandoLivro}`, {
                method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(dto),
            });
        } else {
            res = await fetch(`${API}/livros`, {
                method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(dto),
            });
        }
        if (res.status === 201 || res.status === 204 || res.ok) {
            mostrarToast(estado.editandoLivro ? 'Livro atualizado' : 'Livro cadastrado');
            estado.editandoLivro = null;
            document.getElementById('btn-cancelar-livro').style.display = 'none';
            cancelarLivro();
            carregarLivros();
        } else {
            const json = await res.json().catch(() => null);
            mostrarToast(extrairErros(json) || `Erro (${res.status})`);
        }
    } catch (e) {
        mostrarToast(e.message);
    }
}

function cancelarLivro() {
    estado.editandoLivro = null;
    document.getElementById('livro-form').reset();
    carregarAutoresParaLivro();
    document.getElementById('btn-cancelar-livro').style.display = 'none';
}

function deletarLivro(id) {
    if (!confirm('Excluir este livro?')) return;
    fetch(`${API}/livros/${id}`, { method: 'DELETE' }).then(r => {
        if (r.status === 204) { mostrarToast('Livro excluído'); carregarLivros(); }
        else r.json().then(j => mostrarToast(extrairErros(j) || 'Erro'));
    }).catch(e => mostrarToast(e.message));
}

/* ========== Abas ========== */
function switchTab(tab) {
    const isAutores = tab === 'autores';
    document.getElementById('tab-autores').classList.toggle('hidden', !isAutores);
    document.getElementById('tab-livros').classList.toggle('hidden', isAutores);
    document.getElementById('nav-autores').classList.toggle('active', isAutores);
    document.getElementById('nav-livros').classList.toggle('active', !isAutores);

    if (isAutores) {
        carregarAutores();
    } else {
        carregarAutoresParaLivro();
        carregarLivros();
    }
}

/* ========== Init ========== */
document.getElementById('autor-form').addEventListener('submit', salvarAutor);
document.getElementById('livro-form').addEventListener('submit', salvarLivro);
document.getElementById('btn-cancelar-autor').style.display = 'none';
document.getElementById('btn-cancelar-livro').style.display = 'none';
switchTab('autores');
