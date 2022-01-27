FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install --production
RUN npm install @vue/cli-service
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /var/www/html
EXPOSE 8000
CMD ["nginx", "-g", "daemon off;"]
