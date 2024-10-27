 import { LoginResponse } from '@/Layouts/Login';
import apiClient from '@/utils/apiClient';
import React, { useEffect, useState } from 'react'
import { Card, CardContent, CardFooter } from './ui/card';
import { Button } from './ui/button';
import { CheckIcon, TrashIcon } from '@radix-ui/react-icons';
import { cn } from "@/lib/utils"
import { usenotificationStore } from '@/Store/Store';
export interface TNotification {
    notificationID: number;
    content:        string;
    readStatus:     boolean;
    createdAt:      Date;
}

 const Notifications:React.FC = () => {
     const updatenotification = usenotificationStore(state=>state.updatenotificationAsync)
    const [userId] = useState((JSON.parse(localStorage.getItem('authLogin') ?? JSON.stringify({})) as LoginResponse).userid);
    const updateNotification = async (id:number, readStatus:boolean) => {
        try {
          const response = await apiClient.put(`/notifications/${id}`, {readStatus});
          console.log('Notification updated:', response.data);
          setNotifications((prev)=>{
           return prev.map(i => ({...i,readStatus}))
          });
          return response.data;
        } catch (error) {
          console.error('Error updating notification:', error);
          throw error;
        }
      };
    const deleteNotification = async (id:number) => {
        try {
          const response = await apiClient.delete(`/notifications/${id}`);
          console.log('Notification updated:', response.data);
          setNotifications((prev)=>{
           return prev.filter(i => i.notificationID !== id)
          });
          updatenotification();
          return response.data;
        } catch (error) {
          console.error('Error updating notification:', error);
          throw error;
        }
      };
    const [notifications, setNotifications] = useState<TNotification[]>([]);
    useEffect(() => {
      (async () => {
        const notificationsCount = await apiClient.get(`/notifications/user/${userId}`);
        setNotifications(notificationsCount.data);
      })()
    }, [userId])
   return (
     <main className='ml-20'>
        {notifications.map((notification)=><Card  className={cn('p-2',notification.readStatus ? '':'bg-slate-300')}>
            <CardContent>{notification.content}</CardContent>
            <CardFooter>
                <div className="flex w-full justify-end">
                    <Button onClick={()=>updateNotification(notification.notificationID,true)} className='text-xs px-2 mx-2' variant={"secondary"}><CheckIcon/></Button>
                    <Button onClick={()=>deleteNotification(notification.notificationID)} className='text-xs px-2 mx-2' variant={"destructive"}><TrashIcon/></Button>
                </div>
            </CardFooter>
        </Card>)}
     </main>
   )
 }
 
 export default Notifications