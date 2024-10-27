import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import './index.scss'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Login from './Layouts/Login.tsx'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import Register from './Layouts/Register.tsx'
import ProtectedRoute from './Components/ProtectedRoute.tsx'
import RedirectToHome from './Components/RedirectToHome.tsx'
import Profile from './Components/Profile.tsx'
import TaskList from './Components/TaskList.tsx'
import Notifications from './Components/Notifications.tsx'


const router = createBrowserRouter([
  {
    path:'login',
    element:<RedirectToHome><Login/></RedirectToHome>
  },
  {
    path:'register',
    element:<RedirectToHome><Register/></RedirectToHome>
  },
  {
    path:'',
    element:<ProtectedRoute><App/></ProtectedRoute>,
    
    children:[
      {
        path:'profile',
        element:<Profile/>
      },
      {
        path:'',
        element:<TaskList/>
      },
      {
        path:'inbox',
        element:<Notifications/>
      }
      
    ]
  }
])
const queryClient = new QueryClient()


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} />
    </QueryClientProvider>
  
  </StrictMode>,
)
