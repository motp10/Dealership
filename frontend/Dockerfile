FROM nginx:alpine

COPY . /usr/share/nginx/html
COPY default.conf.template /etc/nginx/templates/default.conf.template

EXPOSE 80

CMD ["/bin/sh", "-c", "envsubst '$$API_BASE_URL' < /usr/share/nginx/html/env.js.template > /usr/share/nginx/html/env.js && nginx -g 'daemon off;' "]
