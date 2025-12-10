import { listenToStoryIds, getItem } from '../services/hnApi.js';

let unsubscribeIds = null;
let allStoryIds = [];
let currentIndex = 0;

export default async function Stories(path) {
  // si había un listener escuchando otra lista, lo cerramos
  if (unsubscribeIds) {
      unsubscribeIds();
      unsubscribeIds = null;
  }
  
  const type = path === '/' ? 'top' : path.replace('/', '');

  const template = `
    <div class="container mt-4">
      <h1 class="mb-4 text-capitalize text-dark fw-bold">${type} Stories</h1>
      
      <div id="stories-list" class="list-group shadow-sm">
        <div class="text-center p-5">
            <div class="spinner-border text-primary" role="status"></div>
            <p class="mt-2 text-muted">Cargando historias...</p>
        </div>
      </div>

      <div class="text-center mt-5 mb-5">
        <button id="load-more-btn" class="btn btn-primary px-4 py-2 d-none">Load more</button>
      </div>
    </div>
  `;

  setTimeout(() => init(type), 0);
  return template;
}

async function init(type) {
    // listen (Top, New, Best)
    unsubscribeIds = listenToStoryIds(type, async (ids) => {
        allStoryIds = ids;
        currentIndex = 0;
        
        const listContainer = document.getElementById('stories-list');
        if (listContainer) {
            listContainer.innerHTML = '';
            await renderNextBatch(10);    // cargamos las primeras 10 
        }
    });
}

async function renderNextBatch(count) {
    const listContainer = document.getElementById('stories-list');
    const nextIds = allStoryIds.slice(currentIndex, currentIndex + count);
    
    const promises = nextIds.map(id => getItem(id));
    const stories = await Promise.all(promises);

    // las pintamos en el DOM
    stories.forEach(story => {
        if (story) {
            const itemHtml = `
                <div class="list-group-item list-group-item-action p-3 border-0 border-bottom">
                    <div class="d-flex w-100 justify-content-between align-items-start">
                        <h5 class="mb-1" style="font-weight: 600;">
                            <a href="${story.url || `/item/${story.id}`}" target="_blank" class="text-decoration-none text-dark">
                                ${story.title}
                            </a>
                        </h5>
                        <span class="badge bg-light text-dark border ms-2">${story.score}</span>
                    </div>
                    <p class="mb-1 text-muted small mt-1">
                        by <a href="/user/${story.by}" class="text-decoration-none fw-bold text-secondary" data-link>${story.by}</a> 
                        <span class="mx-1">•</span> ${new Date(story.time * 1000).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                        <span class="mx-1">•</span> 
                        <a href="/item/${story.id}" class="text-decoration-none text-secondary" data-link>
                            ${story.descendants || 0} comments
                        </a>
                    </p>
                </div>
            `;
            listContainer.insertAdjacentHTML('beforeend', itemHtml);
        }
    });

    currentIndex += count;
    
    // Gestionar botón cargar mas
    const loadMoreBtn = document.getElementById('load-more-btn');
    if (loadMoreBtn) {
        if (currentIndex < allStoryIds.length) {
            loadMoreBtn.classList.remove('d-none');
            loadMoreBtn.onclick = () => renderNextBatch(10);
        } else {
            loadMoreBtn.classList.add('d-none');
        }
    }
}