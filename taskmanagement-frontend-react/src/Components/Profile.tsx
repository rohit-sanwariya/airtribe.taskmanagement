import { ERole, IUserProfile } from '@/types/User';
import apiClient from '@/utils/apiClient'
import React, { useEffect, useState } from 'react'
import { Avatar, AvatarFallback, AvatarImage } from './ui/avatar';
import { Label } from './ui/label';
import { Input } from './ui/input';
import { InputFile } from './InputFile';
import { AxiosResponse } from 'axios';
import { toast } from "sonner"
const Profile:React.FC = ( ) => {
  const [user, setUser] = useState<IUserProfile>({
    firstname: 'John',
    lastname: 'Doe',
    email: 'john.doe@example.com',
    profileImage: '/path/to/image.jpg',
    role: ERole.USER,
  });

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement|HTMLInputElement>) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSave = async () => {
    // Handle save logic (e.g., API call)
    
    await apiClient.put(`/users/${user.id}`, user);
    toast("success")
  };
  useEffect(() => {
    const getProfile = async()=>{
      const data = await apiClient.get<IUserProfile,AxiosResponse<IUserProfile>>('/users/profile');
      const user : IUserProfile =  data.data;
      setUser({...user});
    }
    getProfile();
    return () => {
    }
  }, [])
  
  return (
    <div className="max-w-2xl mx-auto p-6 bg-white rounded-lg shadow-md">
      {/* Avatar */}
      <div className="flex items-center space-x-4">
        <Avatar>
        
        <AvatarImage src={user.profileImage ?? "https://github.com/shadcn.png" }/>
        <AvatarFallback>CN</AvatarFallback>
     
        </Avatar>
        <div>
          <h2 className="text-xl font-bold">
            {user.firstname} {user.lastname}
          </h2>
          <p className="text-sm text-gray-600">{user.email}</p>
        </div>
      </div>

      {/* Form */}
      <form className="mt-6 space-y-4">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {/* First Name */}
          <div>
            <Label className="block text-sm font-medium text-gray-700">
              First Name
            </Label>
            <Input
              type="text"
              name="firstname"
              value={user.firstname}
              onChange={handleChange}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>

          {/* Last Name */}
          <div>
            <Label className="block text-sm font-medium text-gray-700">
              Last Name
            </Label>
            <Input
              type="text"
              name="lastname"
              value={user.lastname}
              onChange={handleChange}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
        </div>

        {/* Email */}
        <div>
          <Label className="block text-sm font-medium text-gray-700">
            Email
          </Label>
          <Input
            type="email"
            name="email"
            value={user.email}
            disabled
            readOnly
            onChange={handleChange}
            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        </div>

        <InputFile/>

        {/* Save Changes Button */}
        <div>
          <button
            type="button"
            onClick={handleSave}
            className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Save Changes
          </button>
        </div>
      </form>
    </div>
  );
};
 

export default Profile