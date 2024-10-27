import { Outlet } from "react-router-dom";
import { Toaster } from "@/Components/ui/sonner"
import { SidebarProvider, SidebarTrigger } from "./Components/ui/sidebar";
import { TaskSidebar } from "./Components/TaskSidebar";

function App() {
  return (
    <>
     
      <SidebarProvider>
      <TaskSidebar />
      <main>
        <SidebarTrigger />
        <Outlet />
      </main>
    </SidebarProvider>
      <Toaster />
    </>
  );
}

export default App;
