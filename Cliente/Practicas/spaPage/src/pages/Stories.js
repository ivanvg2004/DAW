export default async function Stories(path) {
    return `
        <div class="container mt-4">
          <h1 class="mb-4 text-capitalize">${path.replace('/', '')} Stories</h1>
          <div class="alert alert-info">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Ratione necessitatibus totam nostrum ipsam eum blanditiis. Suscipit magni incidunt nulla nemo alias fugit 
            eum iusto neque nostrum a, assumenda cumque totam. <strong>${path}</strong>
          </div>
        </div>
    `;
}