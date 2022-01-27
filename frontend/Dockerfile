FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install --production
RUN npm install @vue/cli-service
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY config/nginx.conf /etc/nginx/nginx.conf
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
