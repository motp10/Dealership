FROM nginx:1.27-alpine

COPY storageFrontend/nginx.conf /etc/nginx/conf.d/default.conf
COPY storageFrontend/index.html storageFrontend/styles.css storageFrontend/app.js /usr/share/nginx/html/

EXPOSE 80