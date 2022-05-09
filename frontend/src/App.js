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
import StudentQuestion from './pages/StudentQuestion';
import StudentAssignment from './pages/StudentAssignment';
import StudentContent from './pages/StudentContent';
import StudentReview from './pages/StudentReview';
import StudentInstructor from './pages/StudentInstructor';
import StudentAboutCourse from './pages/StudentAboutCourse';
import StudentAssignmentAnswer from './pages/StudentAssignmentAnswer';
import StudentCreateQuestion from './pages/StudentCreateQuestion';
import StudentBoard from './pages/StudentBoard';
import StudentCreateReview from './pages/StudentCreateReview';
import StudentEditReview from './pages/StudentEditReview';

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

          <Route path='student/course/:courseId' element={<StudentCourse/>}/>          
          <Route path='review' element={<Review/>}/>
          <Route path='account-manage' element={<AccountManage/>}/>
          <Route path='payment/course/:courseId' element={<Payment />} />

          {/* student my course */}
          <Route path='student/course' element={<Student/>}/>

          {/* student video course */}
          <Route path='student/course/:courseId/video/c/:chapterNo/s/:sectionNo' element={ <VideoCourse/> } />

          {/* student content */}
          <Route path='student/course/:courseId/content' element={ <StudentContent /> } />

          {/* student assignment */}
          <Route path='student/course/:courseId/assignment' element={ <StudentAssignment /> } />
          <Route path='student/course/:courseId/assignment/chapter/:chapterNo' element={ <StudentAssignmentAnswer /> } />
          
          {/* student question board */}
          <Route path='student/course/:courseId/question-board' element={ <StudentQuestion /> } />
          <Route path='student/course/:courseId/question-board/create' element={ <StudentCreateQuestion /> } />
          <Route path='student/course/:courseId/question-board/:questionBoardId' element={ <StudentBoard /> } />
          
          {/* student review */}
          <Route path='student/course/:courseId/review' element={ <StudentReview /> } />
          <Route path='student/course/:courseId/review/create' element={ <StudentCreateReview /> } />
          <Route path='student/course/:courseId/review/:reviewId' element={ <StudentEditReview /> } />
          
          {/* student instructor */}
          <Route path='student/course/:courseId/instructor' element={ <StudentInstructor /> } />
          
          {/* student about course */}
          <Route path='student/course/:courseId/about-course' element={ <StudentAboutCourse /> } />

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