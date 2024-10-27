import { Button } from '@/Components/ui/button';
import { Input } from '@/Components/ui/input';
import { Label } from '@/Components/ui/label';
import { useMutation, UseMutationOptions } from '@tanstack/react-query';
import axios, { AxiosError, AxiosResponse } from 'axios';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

interface LoginData {
  username: string;
  password: string;
}

export interface LoginResponse {
  access_token: string;
  expires_in: string;
  issued_at: string;
  userid: number;
  username: string;
}

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error] = useState<string | null>(null);
  const navigate = useNavigate();
  const loginMutation = async (data: LoginData): Promise<LoginResponse> => {
    const response = await axios.post<LoginResponse, AxiosResponse<LoginResponse>, LoginData>('http://localhost:8080/api/auth/login', data);
    return response.data;
  };

  const mutationOptions: UseMutationOptions<LoginResponse, AxiosError, LoginData> = {
    mutationFn: loginMutation,
    onSuccess: (data) => {
      localStorage.setItem('authLogin', JSON.stringify(data));
      navigate('/');
    },
    onError: () => {
      // Handle error
    },
  };

  const { mutate, isPending, isError } = useMutation(mutationOptions);

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    mutate({ username, password });
  };

  return (
    <div className="flex justify-center items-center h-screen w-screen">
      <form
        onSubmit={handleSubmit}
        className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 
        "
      >
        <h2 className="text-lg mb-4">Login</h2>
        <div className="mb-4">
          <Label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="username"
          >
            Username
          </Label>
          <Input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="username"
            type="email"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
          />
        </div>
        <div className="mb-6">
          <Label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="password"
          >
            Password
          </Label>
          <Input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="password"
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
        </div>
        <div className="mb-4 text-left">
          <Link to="/register" className="text-gray-600 hover:text-gray-900">
            Not a Registered ? Sign Up
          </Link>
        </div>
        {(error || isError) && ( 
          <p className="text-red-500 text-xs italic mb-4">{error || isError}</p>
        )}
        <Button
          className=" text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
          disabled={isPending}
        >
          {isPending ? 'Logging in...' : 'Login'}
        </Button>
      </form>
    </div>
  );
};

export default Login;