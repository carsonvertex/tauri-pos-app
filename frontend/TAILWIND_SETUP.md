# Tailwind CSS Configuration

This project is now configured with **Tailwind CSS v4** for utility-first styling.

## Configuration Files

### 1. `tailwind.config.js`
- Contains the main Tailwind configuration
- Includes custom color palette (primary and secondary)
- Custom font family, shadows, and border radius
- Content paths for purging unused styles

### 2. `vite.config.ts`
- Uses `@tailwindcss/vite` plugin for Tailwind CSS v4
- Integrated with Vite for optimal build performance

### 3. `src/index.css`
- Imports Tailwind CSS using the new v4 syntax: `@import "tailwindcss"`
- Contains custom CSS variables and base styles

## Custom Theme Extensions

### Colors
- **Primary**: Blue-based color palette (50-900)
- **Secondary**: Gray-based color palette (50-900)

### Typography
- Custom sans-serif font stack matching system fonts

### Shadows
- `shadow-soft`: Light shadow for subtle elevation
- `shadow-medium`: Medium shadow for cards
- `shadow-strong`: Strong shadow for modals/overlays

### Border Radius
- `rounded-xl`: 12px border radius
- `rounded-2xl`: 16px border radius

## Usage Examples

```tsx
// Basic utility classes
<div className="bg-white p-6 rounded-xl shadow-soft">
  <h2 className="text-3xl font-bold text-gray-900 mb-4">Title</h2>
  <p className="text-gray-600">Content</p>
</div>

// Custom colors
<button className="bg-primary-500 hover:bg-primary-600 text-white px-4 py-2 rounded">
  Button
</button>

// Responsive design
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
  {/* Grid items */}
</div>
```

## Development

The Tailwind CSS classes are automatically purged in production builds to keep the CSS bundle size minimal. Only the classes actually used in your components will be included in the final build.

## Available Classes

All standard Tailwind CSS classes are available, plus the custom extensions defined in the configuration file. You can use any combination of utility classes to style your components.
