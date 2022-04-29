import Home from './pages/Home';
import Student from './pages/Student';
import Instructor from './pages/Instructor';
import Admin from './pages/Admin';
import Overview from './pages/Overview';
import Review from './pages/Review';
import Search from './pages/Search';
import Payment from './pages/Payment';

import RequireAuth from './components/RequireAuth';
import PersistLogin from './components/PersistLogin';


import {Routes, Route} from 'react-router-dom';
import Unauthorized from './pages/Unauthorized';

import StudentCourse from './pages/StudentCourse';
import AccountManage from './pages/AccountManage';
import VideoCourse from './pages/VideoCourse';
import CreateCourse from './pages/CreateCourse';
import UploadCourseMaterials from "./pages/UploadCourseMaterials";
import PendingCourse from "./pages/PendingCourse";

// import Logout from './pages/Logout';
function App() {
  return (
    <Routes>

      <Route path='/' element={<Home/>}/>

      <Route path='overview/course/:courseId' element={<Overview/>}/>

      <Route path='search' element={<Search/>}/>
      

      {/* public routes */}
      {/* <Route path='login' element={<MuiLogin />} /> */}
      {/* <Route path='register' element={<Register />} /> */}
      <Route path='unauthorized' element={<Unauthorized/>}/>

      {/* public testing routes */}


      {/* private routes */}
      <Route element={<PersistLogin/>}>

        {/* <Route element={<RequireAuth allowRoles={["STUDENT", "INSTRUCTOR", "ADMIN"]} />}>
          <Route path='logout' element={<Logout />} />
        </Route> */}
        <Route element={<RequireAuth allowRoles={["STUDENT"]}/>}>
          <Route path='student/course' element={<Student/>}/>
          <Route path='student/course/:courseId' element={<StudentCourse/>}/>
          <Route path='student/course/video/:id' element={<VideoCourse/>}/>
          <Route path='review' element={<Review/>}/>
          <Route path='account-manage' element={<AccountManage/>}/>
          <Route path='payment/course/:courseId' element={<Payment />} />
        </Route>
        <Route element={<RequireAuth allowRoles={["INSTRUCTOR"]}/>}>
          <Route path='instructor' element={<Instructor/>}/>
          <Route path='instructor/create-course' element={<CreateCourse/>}/>
          <Route path='instructor/create-course/:id' element={<UploadCourseMaterials/>}/>
        </Route>
        <Route element={<RequireAuth allowRoles={["ADMIN"]}/>}>
          <Route path='admin' element={<Admin/>}/>
          <Route path='admin/pending-course/:courseId' element={<PendingCourse/>}/>
        </Route>
      </Route>

    </Routes>
  );
}

export default App;