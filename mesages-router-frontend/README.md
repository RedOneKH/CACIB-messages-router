# Messaging Application Frontend

This is the Angular 17 frontend for the messaging application that communicates with a Spring Boot backend.

## Features

- Display messages received from IBM MQ
- View message details in a pop-up
- Manage partners (add/remove)
- Responsive design with Angular Material

## Technology Stack

- Angular 17
- Angular Material
- RxJS
- TypeScript

## Prerequisites

- Node.js 16+
- npm 8+

## Installation

1. Clone the repository
2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm start
```

4. The application will be available at `http://localhost:4200`

## Docker

The application can be run using Docker:

```bash
docker build -t messaging-app-frontend .
docker run -p 80:80 messaging-app-frontend
```

Or using docker-compose (see root README for full stack setup).

## API Configuration

The API URL is configured in `environment.ts` and `environment.prod.ts`. Update these files to point to your backend API.

## Project Structure

- `src/app/components/messages` - Components for message display and details
- `src/app/components/partners` - Components for partner management
- `src/app/components/shared` - Shared components (dialogs, etc.)
- `src/app/models` - TypeScript interfaces for data models
- `src/app/services` - Services for API communication

## Building for Production

```bash
npm run build
```

The build artifacts will be stored in the `dist/` directory.