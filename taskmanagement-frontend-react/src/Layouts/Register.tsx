import axios, { AxiosError, AxiosResponse } from 'axios';
import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

interface RegisterData {
  firstname: string;
  lastname: string;
  email: string;
  password: string;
}

export interface IAuthResponse {
    access_token: string;
    expires_in: string; 
    issued_at: string; 
    userid: number;
    username: string;
}

const Register = () => {
  const navigate = useNavigate();
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await axios.post<IAuthResponse,AxiosResponse<IAuthResponse>,RegisterData>('http://localhost:8080/api/auth/register', {
        firstname,
        lastname,
        email,
        password,
      });

      setSuccess(response.data.access_token);
      setError(null);
      navigate('/login'); 
    } catch (error) {
      if (error instanceof AxiosError) {
        setError(error.response?.data.message || 'Registration failed');
        setSuccess(null);
      }
    }
  };

  return (
    <div className="flex justify-center items-center h-screen">
      <form
        onSubmit={handleSubmit}
        className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 
        md:w-1/2 
        lg:w-1/3 
        xl:w-1/4 
        sm:w-3/4 
        xs:w-full"
      >
        <h2 className="text-lg mb-4">Register</h2>
        <div className="mb-4">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="firstname"
          >
            Firstname
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="firstname"
            type="text"
            value={firstname}
            onChange={(event) => setFirstname(event.target.value)}
          />
        </div>
        <div className="mb-4">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="lastname"
          >
            Lastname
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="lastname"
            type="text"
            value={lastname}
            onChange={(event) => setLastname(event.target.value)}
          />
        </div>
        <div className="mb-4">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="email"
          >
            Email
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="email"
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
        </div>
        <div className="mb-2">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="password"
          >
            Password
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="password"
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
        </div>
        <div className="mb-4 text-left">
          <Link to="/login" className="text-gray-600 hover:text-gray-900">
            Already a user? Login
          </Link>
        </div>
        {error ? (
          <p className="text-red-500 text-xs italic mb-4">{error}</p>
        ) : (
          success && <p className="text-green-500 text-xs italic mb-4">{success}</p>
        )}
        <button
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
        >
          Register
        </button>
      </form>
    </div>
  );
};

export default Register;