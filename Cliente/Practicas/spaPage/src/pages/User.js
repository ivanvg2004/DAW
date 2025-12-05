import { getUser } from '../services/hnApi.js';

export default async function User(path) {
    const userId = path.split('/')[2];
    
    const template = `
        <div class="container mt-5">
            <div id="user-profile" class="card shadow-sm">
                <div class="card-body text-center p-5">
                    <div class="spinner-border text-primary" role="status"></div>
                    <p class="mt-3">Cargando perfil de <strong>${userId}</strong>...</p>
                </div>
            </div>
            <div class="mt-3">
                <a href="javascript:history.back()" class="btn btn-outline-secondary">← Volver</a>
            </div>
        </div>
    `;

    setTimeout(async () => {
        const user = await getUser(userId);
        const container = document.getElementById('user-profile');

        if (user) {
            const created = new Date(user.created * 1000).toLocaleDateString();

            container.innerHTML = `
                <div class="card-header bg-white border-0 pt-4">
                    <h2 class="fw-bold text-primary">${user.id}</h2>
                    <span class="badge bg-secondary">Karma: ${user.karma}</span>
                </div>
                <div class="card-body">
                    <p class="text-muted">Se unió: ${created}</p>
                    <div class="card-text mt-4 px-md-5 text-start">
                        ${user.about || '<em class="text-muted">Sin descripción.</em>'}
                    </div>
                </div>
            `;
        } else {
            container.innerHTML = `<div class="alert alert-danger m-3">Usuario no encontrado</div>`;
        }
    }, 0);

    return template;
}