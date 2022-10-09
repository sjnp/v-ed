FROM node:16

ENV NODE_OPTIONS=--max_old_space_size=2048

WORKDIR /usr/src/app

COPY . .

RUN npm ci

RUN npm run build

RUN npm install -g serve

CMD ["serve", "-s", "build", "-p", "3000"]