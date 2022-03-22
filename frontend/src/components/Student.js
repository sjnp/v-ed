import { useLocation, useNavigate } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import useRefreshToken from "../hooks/useRefreshToken";
const Student = () => {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();

  const upgradeToInstructor = async () => {
    try {
      const response = await axiosPrivate.put('/api/users/u/change-to-instructor');
      console.log(response.data);
    } catch (err) {
      console.error(err);
      navigate('/login', { state: { from: location }, replace: true });
    }
  }

  
  const refresh = useRefreshToken();
  const gimmeNewAccessToken = async () => {

    const newAccessToken = await refresh();

  }

  return (
    <section>
      <h1>Welcome :D</h1>
      <h3>Student</h3>
      <button onClick={() => upgradeToInstructor()}>
        to be instructor 
      </button>
      <button onClick={() => gimmeNewAccessToken()}>
        refresh
      </button>
    </section>
  )
}

export default Student;