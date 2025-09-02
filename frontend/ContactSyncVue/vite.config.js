// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
      server: {
        proxy: {
          '/principals': 'http://localhost:8080',
          '/submit': 'http://localhost:8080',
        }
      }
})