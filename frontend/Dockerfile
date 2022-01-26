FROM node:lts-alpine

RUN npm install -g http-server

WORKDIR /app

COPY package*.json ./

RUN npm install --production
RUN npm install @vue/cli-service
RUN npm install node-sass

COPY . .

RUN npm run build

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
