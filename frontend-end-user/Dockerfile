FROM node:current-alpine as build

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app


COPY package.json  /usr/src/app/

RUN npm install --quiet
RUN npm install -g @angular/cli@latest

COPY . /usr/src/app

RUN node --max_old_space_size=8000 ./node_modules/@angular/cli/bin/ng build

FROM nginx:latest

COPY --from=build /usr/src/app/dist/notus-angular /var/www/enduser

COPY nginx-end-user.conf /etc/nginx/conf.d/default.conf

EXPOSE 81
