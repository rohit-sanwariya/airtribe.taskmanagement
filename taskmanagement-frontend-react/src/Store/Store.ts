import { LoginResponse } from '@/Layouts/Login';
import { Task } from '@/types/User';
import apiClient from '@/utils/apiClient';
import { create } from 'zustand'

type TNotification = {
    notification: number,
    updatenotification:(notification:number)=>void
    updatenotificationAsync:()=>Awaited<void>
}

type TTaskState = {
  tasks:Task[],
  loading:boolean,
  fetchtasksAsync:()=>Awaited<void>,
  updatetasksAsync:(task:Partial<Task>,method:'put'|'post')=>Awaited<void>,
}

export const usenotificationStore = create<TNotification>((set) => ({
  notification: 0, 
  updatenotification: (newnotification: number) => set({ notification: newnotification }),
  updatenotificationAsync:async()=>{
    const userId = (JSON.parse(localStorage.getItem('authLogin') ?? JSON.stringify({})) as LoginResponse).userid;
    const notificationsCount = await apiClient.get(`/notifications/count/${userId}`);
    set(()=>({notification:notificationsCount.data}))
  }
}))
export const usetasksStore = create<TTaskState>((set) => ({
  tasks: [],
  loading:true, 
 
  fetchtasksAsync:async()=>{
     const response = await apiClient.get('/tasks');
    set(()=>({tasks:response.data,loading:false}))
  },
  updatetasksAsync:async(task:Partial<Task>,method:'put'|'post') =>{
    await apiClient[method](method === 'put' ? `/tasks/${task.taskId}` : '/tasks', {
      ...task,
    
    });
    
  }
}))