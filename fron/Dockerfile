# Используем официальный образ Node.js в качестве базового
FROM node:16 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем исходный код
COPY . .

# Строим приложение
RUN npm run build

# Используем официальный образ Nginx для развертывания
# FROM nginx:alpine

# Копируем сгенерированные файлы из предыдущего этапа
# COPY --from=build /app/build /usr/share/nginx/html

# Открываем порт 50
EXPOSE 3000

# Запускаем Nginx
CMD ["npm", "start"]