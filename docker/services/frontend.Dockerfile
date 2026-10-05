FROM nginx:1.27-alpine

RUN apk add --no-cache gettext

COPY . /usr/share/nginx/html
COPY default.conf.template /etc/nginx/templates/default.conf.template

RUN chown -R nginx:nginx /usr/share/nginx/html

EXPOSE 80

CMD ["/bin/sh", "-c", "envsubst '$$API_BASE_URL' < /usr/share/nginx/html/env.js.template > /usr/share/nginx/html/env.js && nginx -g 'daemon off;' "]