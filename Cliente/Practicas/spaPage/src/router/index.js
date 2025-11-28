import Stories from "../pages/Stories";

const routes = {
    '/': Stories,
    '/top': Stories,
    '/new': Stories,
    '/best': Stories,
};

class Router{
    constructor() {
        this.content = document.getElementById('app');
        this.init();
    }

    init(){
        window.addEventListener('popstate',() => this.render(location.pathname));

        document.body.addEventListener('click', e => {
            if(e.target.matches('[data-link]')) {
                e.preventDefault();
                this.navigateTo(e.target.getAttribute('href'));
            }
        })
        //si es ruta / pasamos a /top
        const path = location.pathname === '/' ? '/top' : location.pathname;
        this.navigateTo(path, false);
    }

    //Cambia URL y renderiza pagina
    navigateTo(url, addToHistory = true) {
        if(addToHistory){
            history.pushState(null, null, url);
        }else{
            history.replaceState(null, null, url);
        }
        this.render(url);
    }

    async render(path) {
        const page = routes[path];

        if(page) {
            this.content.innerHTML = await page(path);
        }else{
            this.content.innerHTML = `<h1>404-Pagina no encontrada</h1>`;
        }
    }
}

export default new Router();