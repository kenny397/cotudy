FROM node:12 AS build
WORKDIR /app
COPY package* ./
RUN npm install --production
RUN npm install @vue/cli-service
COPY public ./public 
COPY src ./src
RUN npm run build

# 2. 빌드 된 파일을 배포용 이미지에 복사
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
