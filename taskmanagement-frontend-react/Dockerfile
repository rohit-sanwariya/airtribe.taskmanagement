# Stage 1: Build the React app
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json (or yarn.lock) to install dependencies
COPY package.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React app using Vite
RUN npm run build

# Stage 2: Serve the built app with a lightweight web server
FROM nginx:alpine

# Copy the built app from the previous stage to the NGINX HTML folder
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 5173
EXPOSE 80

COPY --from=build /app/nginx.conf /etc/nginx/conf.d/default.conf

# Start NGINX and replace the default config to allow serving on port 5173
CMD ["nginx", "-g", "daemon off;"]
