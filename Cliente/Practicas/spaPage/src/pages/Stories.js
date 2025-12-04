import { listenToStoryIds, getItem } from "../services/hnApi.js";

export default async function Stories(path) {
  const type = path === '/' ? 'top' : path.replace('/', '');

  const template = `
    <div class="container mt-4">
      <h1 class="mb-4 text-capitalize text-white">${type} Stories</h1>
      <div id="stories-list" class="list-group">
        <div class="text-center p-5">
            <div class="spinner-border text-primary" role="status"></div>
            <p>Cargando historias...</p>
        </div>
      </div>
      <div class="text-center mt-4 mb-5">
        <button id="load-more-btn" class="btn btn-primary d-none">Load more</button>
      </div>
    </div>
  `;

  setTimeout(() => loadStories(type), 0);

  return template;
}

let allStoryIds = [];
let currentIndex = 0;

async function loadStories(type) {
  listenToStoryIds(type, async (ids) => {
    allStoryIds = ids;
    currentIndex = 0;

    const listContainer = document.getElementById('stories-list');
    if (listContainer) {
      listContainer.innerHTML = '';
      await renderNextBatch(10);
    }
  });
}

async function renderNextBatch(count) {
  const listContainer = document.getElementById('stories-list');
  const nextIds = allStoryIds.slice(currentIndex, currentIndex + count);

  const storiesPromises = nextIds.map(id => getItem(id));
  const stories = await Promise.all(storiesPromises);

  stories.forEach(story => {
    if (story) {
      const itemHtml = `
        <div class="list-group-item list-group-item-action p-3 border-0 border-bottom">
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">
                    <a href="${story.url}" target="_blank" class="text-decoration-none text-dark">
                        ${story.title}
                    </a>
                </h5>
                <small class="text-muted">${story.score} points</small>
            </div>
            <p class="mb-1 text-muted small">
                by <a href="/user/${story.by}" data-link>${story.by}</a> 
                | ${new Date(story.time * 1000).toLocaleDateString()}
                | <a href="/item/${story.id}" data-link>${story.descendants || 0} comments</a>
            </p>
        </div>
      `;
      listContainer.insertAdjacentHTML('beforeend', itemHtml);
    }
  });

  currentIndex += count;

  const loadMoreBtn = document.getElementById('load-more-btn');
  if (loadMoreBtn) {
    loadMoreBtn.classList.remove('d-none');
    loadMoreBtn.onclick = () => renderNextBatch(10);
  }
}