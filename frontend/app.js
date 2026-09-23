const API_BASE_URL = window.API_BASE_URL || (window.__ENV__ && window.__ENV__.API_BASE_URL) || 'http://localhost:8080';

function showResult(elementId, value) {
  const node = document.getElementById(elementId);
  if (!node) return;
  node.textContent = typeof value === 'string' ? value : JSON.stringify(value, null, 2);
}

async function apiRequest(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {})
    },
    ...options
  });

  const text = await response.text();
  let data = null;

  if (text) {
    try {
      data = JSON.parse(text);
    } catch {
      data = text;
    }
  }

  if (!response.ok) {
    const message = data && typeof data === 'object' ? JSON.stringify(data) : text || 'Ошибка запроса';
    throw new Error(message);
  }

  return data;
}

async function createCar(event) {
  event.preventDefault();
  const form = event.currentTarget;
  const formData = new FormData(form);

  const payload = {
    brand: formData.get('brand')?.toString() || '',
    model: formData.get('model')?.toString() || '',
    bodyType: formData.get('bodyType')?.toString() || '',
    paint: formData.get('paint')?.toString() || '',
    fuel: formData.get('fuel')?.toString() || '',
    transmission: formData.get('transmission')?.toString() || '',
    driveLayout: formData.get('driveLayout')?.toString() || '',
    wheel: formData.get('wheel')?.toString() || '',
    clientId: formData.get('clientId')?.toString() || null,
    baseCost: Number(formData.get('baseCost')) || 0
  };

  try {
    const result = await apiRequest('/api/cars', {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    showResult('carResult', result);
  } catch (error) {
    showResult('carResult', `Ошибка: ${error.message}`);
  }
}

async function createDetail(event) {
  event.preventDefault();
  const form = event.currentTarget;
  const formData = new FormData(form);

  const payload = {
    type: formData.get('type')?.toString() || '',
    information: formData.get('information')?.toString() || '',
    cost: Number(formData.get('cost')) || 0,
    id: formData.get('id')?.toString() || null
  };

  try {
    const result = await apiRequest('/api/details', {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    showResult('detailResult', result);
  } catch (error) {
    showResult('detailResult', `Ошибка: ${error.message}`);
  }
}

async function createUser(event) {
  event.preventDefault();
  const form = event.currentTarget;
  const formData = new FormData(form);

  const payload = {
    name: formData.get('name')?.toString() || '',
    password: formData.get('password')?.toString() || '',
    FullName: formData.get('FullName')?.toString() || '',
    Role: formData.get('Role')?.toString() || ''
  };

  try {
    const result = await apiRequest('/api/users', {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    showResult('userResult', result);
  } catch (error) {
    showResult('userResult', `Ошибка: ${error.message}`);
  }
}

async function lookupEntity() {
  const type = document.getElementById('lookupType').value;
  const id = document.getElementById('lookupId').value.trim();

  if (!id) {
    showResult('lookupResult', 'Введите ID для поиска');
    return;
  }

  try {
    const result = await apiRequest(`/${type === 'car' ? 'api/cars' : 'api/details'}/${id}`);
    showResult('lookupResult', result);
  } catch (error) {
    showResult('lookupResult', `Ошибка: ${error.message}`);
  }
}

document.getElementById('carForm').addEventListener('submit', createCar);
document.getElementById('detailForm').addEventListener('submit', createDetail);
document.getElementById('userForm').addEventListener('submit', createUser);
document.getElementById('lookupBtn').addEventListener('click', lookupEntity);

showResult('carResult', 'Готово к работе.');
showResult('detailResult', 'Готово к работе.');
showResult('userResult', 'Готово к работе.');
showResult('lookupResult', 'Введите ID и нажмите Получить.');
