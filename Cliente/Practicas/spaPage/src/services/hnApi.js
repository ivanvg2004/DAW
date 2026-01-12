const BASE_URL = 'https://hacker-news.firebaseio.com/v0';

export function listenToStoryIds(type, callback) {
    const url = `${BASE_URL}/${type}stories.json`;
    
    fetch(url)
        .then(response => response.json())
        .then(ids => {
            callback(ids || []);
        })
        .catch(error => {
            console.error(`Error al cargar ${type}:`, error);
            callback([]);
        });


    return () => {};
}

export async function getItem(id) {
    try {
        const response = await fetch(`${BASE_URL}/item/${id}.json`);
        return await response.json();
    } catch (error) {
        console.error(`Error item ${id}:`, error);
        return null;
    }
}

export async function getUser(id) {
    try {
        const response = await fetch(`${BASE_URL}/user/${id}.json`);
        return await response.json();
    } catch (error) {
        console.error(`Error usuario ${id}:`, error);
        return null;
    }
}

export function listenToItem(id, callback) {
    getItem(id).then(item => callback(item));
    return () => {}; 
}