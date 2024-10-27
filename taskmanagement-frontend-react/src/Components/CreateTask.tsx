import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { Button } from "./ui/button";
import { Dialog, DialogContent, DialogFooter, DialogHeader, DialogTitle } from "./ui/dialog";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import apiClient from "@/utils/apiClient";
import ComboBox from "./ComboBox";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue
} from "@/Components/ui/select";
import { DatePicker } from "./DatePicker";
import { taskDataInitial, UserDTO, TaskPriority, Task } from "@/types/User";
import { usetasksStore } from "@/Store/Store";


export function CreateTask({ open, onSubmit, setopen, task }: {
  setopen: Dispatch<SetStateAction<boolean>>, open: boolean, onSubmit:  (open: boolean) => void, task: Task | null
}) {

  const [taskData, setTaskData] = useState({ ...taskDataInitial });

  const [users, setUsers] = useState<UserDTO[]>([]);
  const updateTaskAsync = usetasksStore(state => state.updatetasksAsync);
  useEffect(() => {
    if (task) {
      setTaskData({
        title: task.title,
        description: task.description,
        dueDate: task.dueDate,
        status: task.status,
        userId: task.userId,
        priority: task.priority
      });
    }
    else {
      setTaskData({ ...taskDataInitial })
    }
  }, [task]);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = event.target;
    setTaskData(prevData => ({ ...prevData, [id]: value }));
  };
  const handleComboInputChange = (value: string) => {
    setTaskData(prevData => ({ ...prevData, 'userId': Number(value) }));
  };
  const handleSelectInputChange = (id:'status'|'priority',value: string) => {
    setTaskData(prevData => ({ ...prevData, [id]: Number(value) }));
  };
  const handleDateInputChange = (value: Date) => {
    setTaskData(prevData => ({ ...prevData, 'dueDate': value }));
  };

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      updateTaskAsync({...taskData,taskId:task?.taskId},task ? 'put':'post');
      onSubmit(false);
    } catch (error) {
      console.error(error);
    }
  };

  const searchUsers = async () => {

    try {
      const response = await apiClient.get(`/users`);
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }

  };
  useEffect(() => {
    searchUsers();
  }, [])



  return (
    <Dialog onOpenChange={(open) => setopen(open)} open={open}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Create Task</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit}>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="title" className="text-right">Title</Label>
              <Input
                id="title"
                value={taskData.title}
                onChange={handleInputChange}
                className="col-span-3"
              />
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="description" className="text-right">Description</Label>
              <Input
                id="description"
                value={taskData.description}
                onChange={handleInputChange}
                className="col-span-3"
              />
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="dueDate" className="text-right">Due Date</Label>

              <DatePicker date={taskData.dueDate} setDate={handleDateInputChange} />
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="status" className="text-right">Status</Label>

              <Select onValueChange={(event)=>handleSelectInputChange('status',event)} value={taskData.status}>
                <SelectTrigger className="w-[180px]">
                  <SelectValue placeholder="Select a status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectGroup>
                    <SelectLabel>Status</SelectLabel>
                    <SelectItem value="PENDING">Pending</SelectItem >
                    <SelectItem value="COMPLETED">Completed</SelectItem >
                  </SelectGroup>
                </SelectContent>
              </Select>
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="Priority" className="text-right">Priority</Label>

              <Select onValueChange={(event)=>handleSelectInputChange('priority',event)} value={taskData.priority}>
                <SelectTrigger className="w-[180px]">
                  <SelectValue placeholder="Select a Priority" />
                </SelectTrigger>
                <SelectContent>
                  <SelectGroup>
                    <SelectLabel>Priority</SelectLabel>
                    {Object.values(TaskPriority).map((priority) => {
                      return <SelectItem value={priority}>{priority.toLocaleLowerCase()}</SelectItem >
                    })}


                  </SelectGroup>
                </SelectContent>
              </Select>
            </div>
            <div className="grid grid-cols-3 items-center gap-4">
              <ComboBox owner={{ id: task?.userId, value: task?.userEmail }} handleComboInputChange={handleComboInputChange} placeholder="Search Owner" options={users.map(i => ({ label: i.email, value: i.id }))} />

            </div>
          </div>
          <DialogFooter>
            <Button type="submit">Save changes</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
