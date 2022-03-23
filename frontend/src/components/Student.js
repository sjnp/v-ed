import { useLocation, useNavigate } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import useLogout from "../hooks/useLogout";
import useRefreshToken from "../hooks/useRefreshToken";

const Student = () => {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();
  const logout = useLogout();

  const upgradeToInstructor = async () => {
    try {
      const response = await axiosPrivate.put('/api/users/u/change-to-instructor');
    } catch (err) {
      console.error(err);
      navigate('/login', { state: { from: location }, replace: true });
    }
  }

  const signOut = async () => {
    await logout();
    navigate('/student');
  }

  return (
    <section>
      <h1>Welcome :D</h1>
      <h3>Student</h3>
      <button onClick={() => upgradeToInstructor()}>
        to be instructor
      </button>
      <button onClick={() => signOut()}>
        Sign Out
      </button>
    </section>
  )
}

export default Student;