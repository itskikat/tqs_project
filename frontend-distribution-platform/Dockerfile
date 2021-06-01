FROM node:current-alpine

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app


COPY package.json  /usr/src/app/

COPY package-lock.json /usr/src/app/
RUN npm install --quiet
RUN npm install -g @angular/cli@latest

COPY . /usr/src/app

CMD [ "ng", "serve", "--host", "0.0.0.0" , "--port", "4201"] 
