const API_URL = '/api/v1/cars';
const grid = document.getElementById('carGrid');
const message = document.getElementById('message');
const count = document.getElementById('carCount');
const searchInput = document.getElementById('searchInput');
let cars = [];

function showMessage(text, kind = '') {
  message.textContent = text;
  message.className = `message${kind ? ` ${kind}` : ''}`;
}

function formatPrice(price) {
  return new Intl.NumberFormat('ru-RU', {
    style: 'currency',
    currency: 'RUB',
    maximumFractionDigits: 0
  }).format(price || 0);
}

function detailText(detail) {
  return detail?.information || detail?.info || detail?.type || '—';
}

function renderCars() {
  const query = searchInput.value.trim().toLocaleLowerCase('ru');
  const filteredCars = cars.filter((car) =>
    `${car.brand || ''} ${car.model || ''}`.toLocaleLowerCase('ru').includes(query)
  );

  count.textContent = filteredCars.length.toString().padStart(2, '0');
  grid.replaceChildren();

  if (!filteredCars.length) {
    showMessage(cars.length ? 'По вашему запросу ничего не найдено.' : 'В каталоге пока нет автомобилей.');
    return;
  }

  showMessage('');
  for (const car of filteredCars) {
    const article = document.createElement('article');
    article.className = 'car-card';

    const top = document.createElement('div');
    top.className = 'car-card-top';
    const badge = document.createElement('span');
    badge.className = `availability${car.isAvailable ? ' is-available' : ''}`;
    badge.textContent = car.isAvailable ? 'В наличии' : 'Под заказ';
    const id = document.createElement('span');
    id.className = 'car-id';
    id.textContent = car.id ? `№ ${car.id.slice(0, 8)}` : 'АВТОМОБИЛЬ';
    top.append(badge, id);

    const heading = document.createElement('h2');
    heading.textContent = `${car.brand || 'Автомобиль'} ${car.model || ''}`.trim();
    const specs = document.createElement('dl');
    specs.className = 'car-specs';
    for (const [label, detail] of [['Кузов', car.body], ['Цвет', car.paint], ['Топливо', car.fuel], ['Коробка', car.transmission]]) {
      const row = document.createElement('div');
      const term = document.createElement('dt');
      term.textContent = label;
      const description = document.createElement('dd');
      description.textContent = detailText(detail);
      row.append(term, description);
      specs.append(row);
    }

    const footer = document.createElement('div');
    footer.className = 'car-card-footer';
    const priceLabel = document.createElement('span');
    priceLabel.textContent = 'Стоимость';
    const price = document.createElement('strong');
    price.textContent = formatPrice(car.totalPrice || car.baseCost);
    footer.append(priceLabel, price);
    article.append(top, heading, specs, footer);
    grid.append(article);
  }
}

async function loadCars() {
  grid.replaceChildren();
  showMessage('Загружаем автомобили…', 'is-loading');
  try {
    const response = await fetch(API_URL);
    if (!response.ok) throw new Error(`Сервер ответил с кодом ${response.status}`);
    cars = await response.json();
    renderCars();
  } catch (error) {
    showMessage(`Не удалось загрузить каталог: ${error.message}`, 'is-error');
  }
}

document.getElementById('refreshButton').addEventListener('click', loadCars);
searchInput.addEventListener('input', renderCars);

loadCars();