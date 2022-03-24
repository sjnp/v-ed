import ResponsiveAppBar from "../components/Appbar"
import { useSelector } from "react-redux"; // ใช้ดึงข้อมูลออกจาก Store
import { useDispatch } from "react-redux"; // ใช้เรียก Method ที่เขียนไว้ใน Reducer
import { setUser } from "../redux/user"; // import Method ที่เขียนไว้ใน Reducer มาเรียกใช้

const Home = () => {
  const user = useSelector((state) => state.user.value)
  const dispatch = useDispatch()
  
  return (
    <section>
      <ResponsiveAppBar/>
      <h1>Welcome :D</h1>
      <h3>Please Login</h3>
    </section>
  )
}

export default Home;