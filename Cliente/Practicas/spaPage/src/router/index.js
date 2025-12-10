import Stories from "../pages/Stories.js";
import User from "../pages/User.js";
import Comments from "../pages/Comments.js";

const routes = {
    '/': Stories,
    '/top': Stories,
    '/new': Stories,
    '/best': Stories,
};

class Router {
    constructor() {
        this.content = document.getElementById('app');
        this.init();
    }

    init() {
        window.addEventListener('popstate', () => this.render(location.pathname));

        document.body.addEventListener('click', e => {
            if (e.target.matches('[data-link]')) {
                e.preventDefault();
                this.navigateTo(e.target.getAttribute('href'));
            }
        });
        
        const path = location.pathname === '/' ? '/top' : location.pathname;
        this.navigateTo(path, false);
    }

    navigateTo(url, addToHistory = true) {
        if (addToHistory) {
            history.pushState(null, null, url);
        } else {
            history.replaceState(null, null, url);
        }
        this.render(url);
    }

    async render(path) {
        let page = routes[path];

        //rutas dinámicas
        if (!page) {
            if (path.startsWith('/user/')) {
                page = User;
            } else if (path.startsWith('/item/')) {
                page = Comments;
            }
        }

        if (page) {
            this.content.innerHTML = await page(path);
        } else {
            this.content.innerHTML = `<h1>404 - Página no encontrada</h1>`;
        }
    }
}

export default new Router();