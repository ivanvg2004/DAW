import { initializeApp } from "firebase/app";
import { getDatabase, ref, onValue, get, child } from "firebase/database";


const app = initializeApp({
    databaseURL: "https://hacker-news.firebaseio.com",
});

const db = getDatabase(app);

export function listenToStoryIds(type, callback) {
    const storiesRef = ref(db, `v0/${type}stories`);

    return onValue(storiesRef, (snapshot) => {
        const ids = snapshot.val();
        callback(ids || []);
    });
}

export async function getItem(id) {
    const itemRef = ref(db, `v0/item/${id}`);
    const snapshot = await get(itemRef);
    return snapshot.val();    
}

export async function getUser(id) {
    const userRef = ref(db,`v0/user/${id}`);
    const snapshot = await get(userRef);
    return snapshot.val();
}