/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          orange: '#FF7A00',
          orangeHover: '#E06B00',
          cream: '#FFF8F0',
          peach: '#FFE7D6',
          beige: '#FFFDFC',
          darkText: '#2C1D14',
          mutedText: '#6C574F',
          darkBg: '#1C120B',
          darkSurface: '#261910',
          darkTextLight: '#FFF8F0'
        }
      },
      fontFamily: {
        sans: ['Poppins', 'Montserrat', 'sans-serif'],
      }
    },
  },
  plugins: [],
}
