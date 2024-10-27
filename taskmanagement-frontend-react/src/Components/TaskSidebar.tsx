import { Calendar, Home, Inbox, Search, LogOut } from "lucide-react"

import {
    Sidebar,
    SidebarContent,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem,
} from "@/Components/ui/sidebar";
import {Link} from "react-router-dom";
import { AvatarIcon } from "@radix-ui/react-icons";
import { useState, useEffect } from "react";
import { usenotificationStore } from "@/Store/Store";
import { LoginResponse } from "@/Layouts/Login";

// Menu items.
const items = [
    {
        title: "Home",
        url: "/",
        icon: Home,  onClick:()=>{
            
        }
    },
    {
        title: "Inbox",
        url: "inbox",
        showCount:true,
        icon: Inbox,  onClick:()=>{
            
        }
    },
    {
        title: "Calendar",
        url: "#",
        icon: Calendar,  onClick:()=>{
            
        }
    },
    {
        title: "Search",
        url: "#",
        icon: Search,  onClick:()=>{
            
        }
    },
    {
        title: "Profile",
        url: "profile",
        icon: AvatarIcon,
        onClick:()=>{
            
        }
    },
    {
        title: "Logout",
        url: "login",
        icon: LogOut,
        onClick:()=>{
            localStorage.clear();
        }
    },
]

export function TaskSidebar() {
  const [userId] = useState((JSON.parse(localStorage.getItem('authLogin') ?? JSON.stringify({})) as LoginResponse).userid);
  const count = usenotificationStore(state=>state.notification);
  const updatenotificationAsync = usenotificationStore(state=>state.updatenotificationAsync)
    
    useEffect(() => {
        updatenotificationAsync()
    }, [userId])
    return (
        <Sidebar variant={"floating"}>
            <SidebarContent>
                <SidebarGroup>
                    <SidebarGroupLabel>Application</SidebarGroupLabel>
                    <SidebarGroupContent>
                        <SidebarMenu>
                            {items.map((item) => (
                                <SidebarMenuItem key={item.title}>
                                    <SidebarMenuButton asChild>
                                        <Link className="text-black" onClick={item?.onClick} to={item.url}>
                                            <item.icon />
                                            <span>{item.title}</span> {item.showCount && <span className="text-blue-500" >({count})</span> }
                                        </Link>
                                    </SidebarMenuButton>
                                </SidebarMenuItem>
                            ))}
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
            </SidebarContent>
        </Sidebar>
    )
}
