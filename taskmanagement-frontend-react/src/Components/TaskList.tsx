import React, { useState } from 'react';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardTitle,
} from './ui/card';
import apiClient from '@/utils/apiClient';
import { CreateTask } from './CreateTask';
import { Button } from './ui/button';
import { PlusIcon, Pencil1Icon } from "@radix-ui/react-icons"
import { MessageSquareIcon } from 'lucide-react';
import { LoginResponse } from '@/Layouts/Login';
import { Task } from '@/types/User';
import { usetasksStore } from '@/Store/Store';



const TaskList = () => {
  const tasks = usetasksStore(s=>s.tasks);
  const fetchTasks = usetasksStore(s=>s.fetchtasksAsync);
  const loading  = usetasksStore(s=>s.loading);
  const [error, setError] = useState<string | null>(null);
  const [open, setopen] = useState<boolean>(false);
  const [currentTask, setTurrentTask] = useState<Task | null>(null);
  const [commentOpenId, setCommentOpenId] = useState<number|null>(null);
  const [comments, setComments] = useState<Comment[]>([]);
  const [newComment, setNewComment] = useState<string>("");
  const [userId] = useState((JSON.parse(localStorage.getItem('authLogin') ?? JSON.stringify({})) as LoginResponse).userid);
 
  React.useEffect(() => {
    fetchTasks();
  }, [fetchTasks]);
  const handleSubmit = (open: boolean) => {
    setopen(open);
    fetchTasks();
  }
  const handleCommentSubmit = async (taskId: number) =>{
   await apiClient.post('/comments',{
    content: newComment,
    authorUserID: userId,
    taskID: taskId
   })
   setNewComment("");
   getCommentsByTaskId(taskId)
  }
  const getCommentsByTaskId = async (taskId:number) =>{
   const comments = await apiClient.get(`/comments/task/${taskId}`);
    setCommentOpenId(taskId);
    setComments(comments.data);
  }
  const handleCommentOpen = async (task:Task) => {
    await getCommentsByTaskId(task.taskId)
   
  }
  return (
    <div className="container ml-20">
      <CreateTask task={currentTask} open={open} setopen={setopen} onSubmit={handleSubmit} />
      <div className="flex justify-end">
        <Button className='my-2' onClick={() => setopen(true)} ><PlusIcon /> Add Task</Button>
      </div>
      <h1 className="text-3xl font-bold mb-4">Task List</h1>
      {loading ? (
        <div className="flex justify-center">
          <svg
            className="animate-spin h-5 w-5 mr-3 border-2 border-gray-600 rounded-full"
            viewBox="0 0 24 24"
          />
        </div>
      ) : error ? (
        <div className="text-red-500">{error}</div>
      ) : (
        <div className="flex w-full flex-wrap ">
          {tasks.map((task) => (
            <Card
              key={task.taskId}
              className="w-full my-4 md:w-1/2 xl:w-1/3 p-4 bg-white rounded-lg shadow-md"
            >
              <div className="flex justify-between">
                <CardTitle className="text-lg font-bold mb-2">{task.title}</CardTitle> <Pencil1Icon onClick={() => {
                  setopen(true);
                  setTurrentTask(task)
                }
                } className='text-yellow-800' />
              </div>
              <CardDescription className="text-gray-600">{task.description}</CardDescription>
              <CardContent>
                <span className="text-gray-600">
                  Due Date: {new Date(task.dueDate).toLocaleString()}
                </span>
                <span
                  className={`px-2 py-1 text-sm rounded-lg ${task.status === 'PENDING'
                      ? 'bg-yellow-100 text-yellow-800'
                      : task.status === 'COMPLETED'
                        ? 'bg-green-100 text-green-800'
                        : 'bg-gray-100 text-gray-800'
                    }`}
                >
                  {task.status}
                </span>
              </CardContent>
              <CardFooter className="flex justify-between mt-2">
                <MessageSquareIcon onClick={()=>handleCommentOpen(task)} />
                {task.taskId === commentOpenId && (
                  <div className="mt-2">
                    <textarea
                      value={newComment}
                      onChange={(e) => setNewComment(e.target.value)}
                      placeholder="Write a comment..."
                      className="w-full p-2 bg-gray-100 rounded-lg"
                    />
                    <Button
                      onClick={() => handleCommentSubmit(task.taskId)}
                      className="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    >
                      Post Comment
                    </Button>
                    {comments.map((comment) => (
                      <div key={comment.commentId} className="bg-gray-100 p-2 rounded-lg mt-2">
                        {comment.content}
                      </div>
                    ))}
                  </div>
                )}
              </CardFooter>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};
export default TaskList