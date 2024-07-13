(async () => {
  const url = "http://localhost:6060/restaurants";
  const response = await fetch(url);
  const restaurants = await response.json();
  const element = document.getElementById("app");

  element.innerHTML = `
${restaurants
  .map(
    (restaurant) =>
      `
  <p>
  ${restaurant.id}
  ${restaurant.name}
  ${restaurant.address}
  </p>
`
  )
  .join("")}
`;
})();
