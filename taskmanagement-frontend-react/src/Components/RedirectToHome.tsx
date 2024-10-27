import { Navigate, useLocation } from 'react-router-dom';

const RedirectToHome:React.FC<{children:React.ReactNode}> = ({ children }:{children:React.ReactNode}) => {
  const isLoggedIn = !!localStorage.getItem('authLogin');
  const location = useLocation();

  if (isLoggedIn) {
    return (
      <Navigate
        to="/"
        replace
        state={{ from: location }}
      />
    );
  }

  return children;
};

export default RedirectToHome;