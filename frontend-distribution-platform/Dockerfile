FROM node:current-alpine as build

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app


COPY package.json  /usr/src/app/

COPY package-lock.json /usr/src/app/
RUN npm install --quiet
RUN npm install -g @angular/cli@latest

COPY . /usr/src/app

RUN npm run build

FROM nginx:latest

COPY --from=build /usr/src/app/dist /var/www/distribution
COPY nginx-distribution.conf etc/nginx/conf.d/default.conf
EXPOSE 80
