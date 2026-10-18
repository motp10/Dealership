const CARS_API = '/api/cars';
const DETAILS_API = '/api/details';
const carRows = document.getElementById('carRows');
const message = document.getElementById('message');
const carCount = document.getElementById('carCount');
const searchInput = document.getElementById('searchInput');
const dialog = document.getElementById('carDialog');
const form = document.getElementById('carForm');
const formMessage = document.getElementById('formMessage');
const addButton = document.getElementById('addButton');
const detailRows = document.getElementById('detailRows');
const detailsMessage = document.getElementById('detailsMessage');
const detailDialog = document.getElementById('detailDialog');
const detailForm = document.getElementById('detailForm');
const detailFormMessage = document.getElementById('detailFormMessage');
const carSearchForm = document.getElementById('carSearchForm');
let cars = [];
let details = [];
let detailsAvailable = true;
let detailsLoaded = false;

const fallbackDetails = {
  BODY: ['Sedan'],
  FUEL: ['Petrol'],
  TRANSMISSION: ['Automatic'],
  DRIVELAYOUT: ['AWD'],
  COLOR: ['Black'],
  WHEEL: []
};

function setMessage(text, kind = '') {
  message.textContent = text;
  message.className = `message${kind ? ` is-${kind}` : ''}`;
}

function setDetailsMessage(text, kind = '') {
  detailsMessage.textContent = text;
  detailsMessage.className = `message${kind ? ` is-${kind}` : ''}`;
}

async function api(path, options = {}) {
  const response = await fetch(path, {
    ...options,
    headers: {
      ...(options.body ? { 'Content-Type': 'application/json' } : {}),
      ...options.headers
    }
  });
  if (!response.ok) {
    const detail = await response.text();
    const reason = response.status === 502 || response.status === 503
      ? 'Сервис склада сейчас недоступен. Проверьте, что storageService запущен.'
      : detail || `Сервер ответил с кодом ${response.status}`;
    throw new Error(reason);
  }
  if (response.status === 204) return null;
  const body = await response.text();
  if (!body) return null;
  try { return JSON.parse(body); } catch { return body; }
}

function formatPrice(value) {
  return new Intl.NumberFormat('ru-RU', { style: 'currency', currency: 'RUB', maximumFractionDigits: 0 }).format(value || 0);
}

function appendCell(row, content, className = '') {
  const cell = document.createElement('td');
  if (className) cell.className = className;
  if (content instanceof Node) cell.append(content);
  else cell.textContent = content;
  row.append(cell);
}

function twoLines(primary, secondary, primaryClass = 'spec-primary') {
  const wrapper = document.createElement('div');
  const first = document.createElement('span');
  first.className = primaryClass;
  first.textContent = primary || '—';
  const second = document.createElement('span');
  second.className = 'spec-secondary';
  second.textContent = secondary || '—';
  wrapper.append(first, second);
  return wrapper;
}

function renderCars() {
  const query = searchInput.value.trim().toLocaleLowerCase('ru');
  const filtered = cars.filter((car) =>
    `${car.brand} ${car.model} ${car.id}`.toLocaleLowerCase('ru').includes(query)
  );
  carRows.replaceChildren();
  carCount.textContent = filtered.length.toString().padStart(2, '0');

  for (const car of filtered) {
    const row = document.createElement('tr');
    const vehicle = document.createElement('div');
    const name = document.createElement('span');
    name.className = 'vehicle-name';
    name.textContent = `${car.brand || 'Автомобиль'} ${car.model || ''}`.trim();
    const id = document.createElement('span');
    id.className = 'vehicle-id';
    id.textContent = car.id || '';
    vehicle.append(name, id);
    appendCell(row, vehicle);
    appendCell(row, twoLines(car.body, car.drivelayout));
    appendCell(row, twoLines(car.fuel, car.transmission));
    appendCell(row, car.paint || '—');
    appendCell(row, formatPrice(car.price), 'price-cell');

    const actions = document.createElement('div');
    actions.className = 'row-actions';
    const edit = document.createElement('button');
    edit.className = 'row-action';
    edit.type = 'button';
    edit.textContent = 'Изм.';
    edit.setAttribute('aria-label', `Изменить ${car.brand} ${car.model}`);
    edit.addEventListener('click', () => openEditor(car));
    const view = document.createElement('button');
    view.className = 'row-action';
    view.type = 'button';
    view.textContent = 'Просм.';
    view.title = 'Открыть карточку автомобиля';
    view.addEventListener('click', () => showCar(car.id));
    const remove = document.createElement('button');
    remove.className = 'row-action delete';
    remove.type = 'button';
    remove.textContent = '×';
    remove.title = 'Удалить автомобиль';
    remove.setAttribute('aria-label', `Удалить ${car.brand} ${car.model}`);
    remove.addEventListener('click', () => deleteCar(car));
    actions.append(view, edit, remove);
    appendCell(row, actions);
    carRows.append(row);
  }

  if (!filtered.length) {
    setMessage(cars.length ? 'По запросу ничего не найдено.' : 'Склад пуст. Добавьте первую запись.', '');
  } else {
    setMessage('');
  }
}

async function loadRegistry() {
  setMessage('Загружаем реестр…', 'loading');
  try {
    const loadedCars = await api(`${CARS_API}/search`, {
      method: 'POST'
    });
    cars = Array.isArray(loadedCars) ? loadedCars : [];
    addButton.disabled = false;
    try {
      const loadedDetails = await api(DETAILS_API);
      details = Array.isArray(loadedDetails) ? loadedDetails : [];
      detailsAvailable = true;
      detailsLoaded = true;
    } catch {
      details = [];
      detailsAvailable = false;
      detailsLoaded = false;
    }
    addButton.disabled = false;
    populateDetailOptions();
    renderDetails();
    renderCars();
    if (!detailsAvailable) {
      setMessage('Справочник комплектаций недоступен. Для формы доступны базовые значения.', 'error');
    }
  } catch (error) {
    cars = [];
    carRows.replaceChildren();
    carCount.textContent = '—';
    addButton.disabled = true;
    setMessage(error.message, 'error');
  }
}

async function searchCars(event) {
  event.preventDefault();
  const values = Object.fromEntries(new FormData(carSearchForm));
  const maxPrice = values.maxPrice ? Number(values.maxPrice) : 0;
  const request = {
    brand: values.brand.trim(),
    model: values.model.trim(),
    bodyType: values.bodyType,
    fuel: values.fuel,
    transmission: values.transmission,
    driveLayout: values.driveLayout,
    paint: values.paint,
    wheel: values.wheel,
    ...(maxPrice > 0 ? { maxPrice } : {})
  };
  setMessage('Ищем автомобили…', 'loading');
  try {
    const result = await api(`${CARS_API}/search`, { method: 'POST', body: JSON.stringify(request) });
    cars = Array.isArray(result) ? result : [];
    renderCars();
  } catch (error) {
    setMessage(error.message, 'error');
  }
}

const detailFields = [
  ['bodyType', 'BODY'],
  ['fuel', 'FUEL'],
  ['transmission', 'TRANSMISSION'],
  ['driveLayout', 'DRIVELAYOUT'],
  ['paint', 'COLOR']
];

function canonicalDetailType(type) {
  const value = (type || '').toLocaleLowerCase('en');
  return ({
    body: 'BODY',
    paint: 'COLOR',
    fuel: 'FUEL',
    transmission: 'TRANSMISSION',
    drivelayout: 'DRIVELAYOUT',
    wheel: 'WHEEL'
  })[value] || value.toUpperCase();
}

function detailOperationType(type) {
  const value = (type || '').replace(/\s+/g, '').toLocaleLowerCase('en');
  return ({
    body: 'body',
    paint: 'paint',
    color: 'paint',
    fuel: 'fuel',
    transmission: 'transmission',
    drivelayout: 'drivelayout',
    wheel: 'wheel',
    interior: 'interior',
    spoiler: 'spoiler',
    steeringwheel: 'Steering wheel'
  })[value] || type;
}

function populateDetailOptions() {
  for (const [field, type] of detailFields) {
    const select = form.elements.namedItem(field);
    const current = select.value;
    const optional = field === 'wheel';
    select.replaceChildren();
    if (optional) {
      const empty = document.createElement('option');
      empty.value = '';
      empty.textContent = 'Не задано';
      select.append(empty);
    } else {
      const placeholder = document.createElement('option');
      placeholder.value = '';
      placeholder.textContent = 'Выберите значение';
      placeholder.disabled = true;
      placeholder.selected = true;
      select.append(placeholder);
    }
    const values = [...new Set(detailValues(type))];
    for (const value of values) {
      const option = document.createElement('option');
      option.value = value;
      option.textContent = value;
      select.append(option);
    }
    if (current && values.includes(current)) select.value = current;

    const filterSelect = carSearchForm.elements.namedItem(field);
    const filterCurrent = filterSelect.value;
    filterSelect.replaceChildren(new Option(field === 'wheel' ? 'Любые' : 'Любой', ''));
    for (const value of values) filterSelect.append(new Option(value, value));
    if (values.includes(filterCurrent)) filterSelect.value = filterCurrent;
  }
}

function detailValues(type) {
  return [...new Set([
    ...details.filter((detail) => canonicalDetailType(detail.detailType) === type).map((detail) => detail.detailInfo),
    ...(!detailsAvailable ? fallbackDetails[type] || [] : [])
  ])];
}

function renderDetails() {
  const query = document.getElementById('detailSearchInput').value.trim().toLocaleLowerCase('ru');
  const filtered = details.filter((detail) =>
    `${detail.detailType} ${detail.detailInfo} ${detail.id} ${(detail.compatibleModels || []).join(' ')}`.toLocaleLowerCase('ru').includes(query)
  );
  detailRows.replaceChildren();
  document.getElementById('detailCount').textContent = filtered.length.toString().padStart(2, '0');
  for (const detail of filtered) {
    const row = document.createElement('tr');
    appendCell(row, detail.detailType || '—');
    appendCell(row, detail.detailInfo || '—');
    appendCell(row, (detail.compatibleModels || []).join(', ') || 'Все модели');
    appendCell(row, formatPrice(detail.cost), 'price-cell');
    const actions = document.createElement('div');
    actions.className = 'row-actions';
    const edit = document.createElement('button');
    edit.className = 'row-action';
    edit.type = 'button';
    edit.textContent = 'Изм.';
    edit.setAttribute('aria-label', `Изменить ${detail.detailType}: ${detail.detailInfo}`);
    edit.addEventListener('click', () => editDetail(detail.id));
    const remove = document.createElement('button');
    remove.className = 'row-action delete';
    remove.type = 'button';
    remove.textContent = '×';
    remove.title = 'Удалить деталь';
    remove.setAttribute('aria-label', `Удалить ${detail.detailInfo}`);
    remove.addEventListener('click', () => deleteDetail(detail));
    actions.append(edit, remove);
    appendCell(row, actions);
    detailRows.append(row);
  }
  if (!filtered.length) setDetailsMessage(details.length ? 'По запросу ничего не найдено.' : 'Справочник пуст. Добавьте первую деталь.');
  else setDetailsMessage('');
}

function openEditor(car = null) {
  form.reset();
  formMessage.textContent = '';
  document.getElementById('carId').value = car?.id || '';
  document.getElementById('dialogTitle').textContent = car ? 'Изменить автомобиль' : 'Новый автомобиль';
  if (car) {
    form.elements.namedItem('brand').value = car.brand || '';
    form.elements.namedItem('model').value = car.model || '';
    form.elements.namedItem('bodyType').value = car.body || '';
    form.elements.namedItem('fuel').value = car.fuel || '';
    form.elements.namedItem('transmission').value = car.transmission || '';
    form.elements.namedItem('driveLayout').value = car.drivelayout || '';
    form.elements.namedItem('paint').value = car.paint || '';
  }
  dialog.showModal();
}

async function deleteCar(car) {
  if (!confirm(`Удалить ${car.brand} ${car.model} со склада?`)) return;
  try {
    await api(`${CARS_API}/${encodeURIComponent(car.id)}`, { method: 'DELETE' });
    await loadRegistry();
  } catch (error) {
    setMessage(error.message, 'error');
  }
}

async function showCar(id) {
  try {
    const car = await api(`${CARS_API}/${encodeURIComponent(id)}`);
    showRecord('АВТОМОБИЛЬ', `${car.brand || ''} ${car.model || ''}`.trim(), [
      ['ID', car.id], ['Кузов', car.body], ['Привод', car.drivelayout], ['Топливо', car.fuel],
      ['Коробка передач', car.transmission], ['Цвет', car.paint], ['Стоимость', formatPrice(car.price)]
    ]);
  } catch (error) {
    setMessage(error.message, 'error');
  }
}

function showRecord(kind, title, fields) {
  document.getElementById('recordEyebrow').textContent = kind;
  document.getElementById('recordTitle').textContent = title || 'Запись';
  const list = document.getElementById('recordFields');
  list.replaceChildren();
  for (const [label, value] of fields) {
    const term = document.createElement('dt');
    term.textContent = label;
    const description = document.createElement('dd');
    description.textContent = value ?? '—';
    list.append(term, description);
  }
  document.getElementById('recordDialog').showModal();
}

function openDetailEditor(detail = null) {
  detailForm.reset();
  detailFormMessage.textContent = '';
  detailForm.elements.namedItem('id').value = detail?.id || '';
  document.getElementById('detailDialogTitle').textContent = detail ? 'Изменить деталь' : 'Новая деталь';
  if (detail) {
    detailForm.elements.namedItem('type').value = detailOperationType(detail.detailType);
    detailForm.elements.namedItem('information').value = detail.detailInfo || '';
    detailForm.elements.namedItem('cost').value = detail.cost ?? '';
    detailForm.elements.namedItem('compatibleModels').value = (detail.compatibleModels || []).join(', ');
  }
  detailDialog.showModal();
}

async function editDetail(id) {
  try {
    const detail = await api(`${DETAILS_API}/${encodeURIComponent(id)}`);
    openDetailEditor(detail);
  } catch (error) {
    setDetailsMessage(error.message, 'error');
  }
}

async function deleteDetail(detail) {
  if (!confirm(`Удалить деталь «${detail.detailInfo}»?`)) return;
  try {
    await api(DETAILS_API, { method: 'DELETE', body: JSON.stringify(detail.id) });
    await loadDetails();
  } catch (error) {
    setDetailsMessage(error.message, 'error');
  }
}

async function loadDetails() {
  setDetailsMessage('Загружаем справочник…', 'loading');
  try {
    const loadedDetails = await api(DETAILS_API);
    details = Array.isArray(loadedDetails) ? loadedDetails : [];
    detailsAvailable = true;
    detailsLoaded = true;
    populateDetailOptions();
    renderDetails();
  } catch (error) {
    details = [];
    detailsAvailable = false;
    detailsLoaded = false;
    renderDetails();
    setDetailsMessage(error.message, 'error');
  }
}

async function saveDetail(event) {
  event.preventDefault();
  detailFormMessage.textContent = '';
  const values = Object.fromEntries(new FormData(detailForm));
  const id = values.id;
  const request = {
    type: values.type,
    information: values.information.trim(),
    cost: Number(values.cost),
    compatitiveModels: values.compatibleModels.split(',').map((model) => model.trim()).filter(Boolean),
    ...(id ? { id } : {})
  };
  const button = document.getElementById('saveDetailButton');
  button.disabled = true;
  try {
    await api(DETAILS_API, { method: id ? 'PATCH' : 'POST', body: JSON.stringify(request) });
    detailDialog.close();
    await loadDetails();
  } catch (error) {
    detailFormMessage.textContent = error.message;
  } finally {
    button.disabled = false;
  }
}

async function saveCar(event) {
  event.preventDefault();
  formMessage.textContent = '';
  const formData = new FormData(form);
  const id = document.getElementById('carId').value;
  const request = {
    brand: formData.get('brand').trim(),
    model: formData.get('model').trim(),
    bodyType: formData.get('bodyType'),
    fuel: formData.get('fuel'),
    transmission: formData.get('transmission'),
    driveLayout: formData.get('driveLayout'),
    paint: formData.get('paint'),
    wheel: formData.get('wheel'),
    ...(id ? { carID: id } : {})
  };

  const saveButton = document.getElementById('saveButton');
  saveButton.disabled = true;
  try {
    await api(CARS_API, { method: id ? 'PATCH' : 'POST', body: JSON.stringify(request) });
    dialog.close();
    await loadRegistry();
  } catch (error) {
    formMessage.textContent = error.message;
  } finally {
    saveButton.disabled = false;
  }
}

document.getElementById('refreshButton').addEventListener('click', loadRegistry);
document.getElementById('refreshDetailsButton').addEventListener('click', loadDetails);
document.getElementById('searchInput').addEventListener('input', renderCars);
document.getElementById('detailSearchInput').addEventListener('input', renderDetails);
carSearchForm.addEventListener('submit', searchCars);
document.getElementById('clearFilters').addEventListener('click', () => { carSearchForm.reset(); loadRegistry(); });
addButton.addEventListener('click', () => openEditor());
form.addEventListener('submit', saveCar);
document.getElementById('cancelButton').addEventListener('click', () => dialog.close());
document.getElementById('closeDialog').addEventListener('click', () => dialog.close());
document.getElementById('addDetailButton').addEventListener('click', () => openDetailEditor());
detailForm.addEventListener('submit', saveDetail);
document.getElementById('cancelDetailButton').addEventListener('click', () => detailDialog.close());
document.getElementById('closeDetailDialog').addEventListener('click', () => detailDialog.close());
document.getElementById('closeRecordDialog').addEventListener('click', () => document.getElementById('recordDialog').close());
document.querySelectorAll('.view-tab').forEach((tab) => tab.addEventListener('click', () => {
  document.querySelectorAll('.view-tab').forEach((item) => {
    const active = item === tab;
    item.classList.toggle('is-active', active);
    item.setAttribute('aria-pressed', String(active));
  });
  document.getElementById('carsView').hidden = tab.dataset.view !== 'carsView';
  document.getElementById('detailsView').hidden = tab.dataset.view !== 'detailsView';
  if (tab.dataset.view === 'detailsView' && !detailsLoaded) loadDetails();
}));

loadRegistry();