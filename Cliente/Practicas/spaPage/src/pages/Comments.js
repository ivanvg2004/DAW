import { getItem } from "../services/hnApi.js";

export default async function Comments(path) {
    // URL = /item/12345 -> itemId = 12345
    const itemId = path.split('/')[2];

    const template = `
        <div class="container mt-4 mb-5">
            <div id="story-details">
                <div class="text-center p-5">
                    <div class="spinner-border text-primary" role="status"></div>
                    <p class="mt-3">Cargando debate...</p>
                </div>
            </div>
            
            <h4 class="mt-4 mb-3">Comentarios</h4>
            <div id="comments-container" class="list-group list-group-flush border-top">
            </div>
        </div>
    `;

    setTimeout(() => loadStoryAndComments(itemId), 0);

    return template;
}

// ESTA ES LA FUNCIÓN QUE TE FALTABA
async function loadStoryAndComments(id) {
    const story = await getItem(id);
    const storyContainer = document.getElementById('story-details');
    const commentsContainer = document.getElementById('comments-container');

    if (!story || !storyContainer) return;

    // 1. Pintamos la Cabecera (La Noticia)
    storyContainer.innerHTML = `
        <div class="card shadow-sm border-primary">
            <div class="card-body">
                <h2 class="card-title">
                    <a href="${story.url || '#'}" target="_blank" class="text-decoration-none">
                        ${story.title}
                    </a>
                </h2>
                <div class="text-muted small mb-3">
                    ${story.score} puntos por <a href="/user/${story.by}" data-link>${story.by}</a> 
                    | ${new Date(story.time * 1000).toLocaleString()}
                </div>
                ${story.text ? `<div class="card-text bg-light p-3 rounded">${story.text}</div>` : ''}
            </div>
        </div>
    `;

    // 2. Iniciamos la carga recursiva de comentarios
    if (story.kids && story.kids.length > 0) {
        commentsContainer.innerHTML = ''; // Limpiamos
        await renderComments(story.kids, commentsContainer);
    } else {
        commentsContainer.innerHTML = '<div class="p-3 text-muted">No hay comentarios aún.</div>';
    }
}

async function renderComments(commentsIds, container) {
    const promises = commentsIds.map(id => getItem(id));
    const comments = await Promise.all(promises);

    for(const comment of comments) {
        if (!comment || comment.deleted) continue;

        const commentDiv = document.createElement('div');
        commentDiv.className = 'list-group-item ps-3 pe-0 py-3 border-0';

        commentDiv.innerHTML = `
            <div class="d-flex w-100 justify-content-between">
                <small class="fw-bold text-primary">
                    <a href="/user/${comment.by}" data-link class="text-decoration-none">${comment.by}</a>
                </small>
                <small class="text-muted">${new Date(comment.time * 1000).toLocaleTimeString()}</small>
            </div>
            <div class="mt-1 text-break comment-text">${comment.text || ''}</div>
            
            <div class="ms-4 mt-2 border-start border-2 ps-3" id="replies-${comment.id}"></div>
        `;

        container.appendChild(commentDiv);

        if(comment.kids && comment.kids.length > 0) {
            const repliesContainer = commentDiv.querySelector(`#replies-${comment.id}`);
            renderComments(comment.kids, repliesContainer);
        }
    }
}