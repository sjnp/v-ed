import Home from './pages/Home';
import Student from './pages/Student';
import Instructor from './pages/Instructor';
import Admin from './pages/Admin';
import Overview from './pages/Overview';
import Search from './pages/Search';
import Payment from './pages/Payment';

import RequireAuth from './components/RequireAuth';
import PersistLogin from './components/PersistLogin';

import {Routes, Route} from 'react-router-dom';
import Unauthorized from './pages/Unauthorized';

import AccountManage from './pages/AccountManage';
import AccountManageProfile from './pages/AccountManageProfile';
import AccountManageVerifyPassword from './pages/AccountManageVerifyPassword';
import AccountManageNewPassword from './pages/AccountManageNewPassword';
import AccountManageInstructor from './pages/AccountManageInstructor';
import AccountManageInstructorActive from './pages/AccountManageInstructorActive';
import AccountManageInstructorBank from './pages/AccountManageInstructorBank';
import AccountManageInstructorTransaction from './pages/AccountManageInstructorTransaction';
import StudentVideoCourse from './pages/StudentVideoCourse';
import CreateCourse from './pages/CreateCourse';
import UploadCourseMaterials from "./pages/UploadCourseMaterials";
import PendingCourse from "./pages/PendingCourse";
import StudentPost from './pages/StudentPost';
import StudentAssignment from './pages/StudentAssignment';
import StudentContent from './pages/StudentContent';
import StudentReview from './pages/StudentReview';
import StudentAboutCourse from './pages/StudentAboutCourse';
import StudentAnswer from './pages/StudentAnswer';
import StudentCreatePost from './pages/StudentCreatePost';
import StudentPostComment from './pages/StudentPostComment';
import StudentCreateReview from './pages/StudentCreateReview';
import StudentEditReview from './pages/StudentEditReview';
import PaymentSuccess from './pages/PaymentSuccess';
import InstructorAssignmentCourse from './pages/InstructorAssignmentCourse';
import InstructorAssignmentChapter from './pages/InstructorAssignmentChapter';
import InstructorAssignmentAnswer from './pages/InstructorAssignmentAnswer';
import InstructorPost from './pages/InstructorPost';
import InstructorPostComment from './pages/InstructorPostComment';
import InstructorReview from './pages/InstructorReview';

const App = () => {
  return (
    <Routes>

      {/* public routes */}
      <Route path='/' element={ <Home /> } />
      <Route path='overview/course/:courseId' element={ <Overview /> } />
      <Route path='search' element={ <Search /> } />
      <Route path='unauthorized' element={ <Unauthorized /> } />
      
      {/* private routes */}
      <Route element={ <PersistLogin/> }>
        
        {/* student role */}
        <Route element={<RequireAuth allowRoles={["STUDENT"]}/>}>

          {/* account management */}
          <Route path='account-manage' element={ <AccountManage/> } />
          {/* account management new */}
          <Route path='account-manage/profile' element={ <AccountManageProfile/> } />
          <Route path='account-manage/profile/verify-password' element={ <AccountManageVerifyPassword/> } />
          <Route path='account-manage/profile/new-password' element={ <AccountManageNewPassword/> } />
          
          <Route path='account-manage/instructor' element={ <AccountManageInstructor/> } />
          <Route path='account-manage/instructor/active' element={ <AccountManageInstructorActive/> } />

          {/* payment */}
          <Route path='payment/course/:courseId' element={ <Payment /> } />
          <Route path='payment/course/:courseId/success' element={ <PaymentSuccess /> } />

          {/* student my course */}
          <Route path='student/course' element={ <Student/> } />

          {/* student video course */}
          <Route path='student/course/:courseId/video/c/:chapterIndex/s/:sectionIndex' element={ <StudentVideoCourse/> } />

          {/* student content */}
          <Route path='student/course/:courseId/content' element={ <StudentContent /> } />

          {/* student assignment */}
          <Route path='student/course/:courseId/assignment' element={ <StudentAssignment /> } />
          <Route path='student/course/:courseId/assignment/chapter/:chapterIndex' element={ <StudentAnswer /> } />
          
          {/* student post */}
          <Route path='student/course/:courseId/post' element={ <StudentPost /> } />
          <Route path='student/course/:courseId/post/create' element={ <StudentCreatePost /> } />
          <Route path='student/course/:courseId/post/:postId' element={ <StudentPostComment /> } />
          
          {/* student review */}
          <Route path='student/course/:courseId/review' element={ <StudentReview /> } />
          <Route path='student/course/:courseId/review/create' element={ <StudentCreateReview /> } />
          <Route path='student/course/:courseId/review/:reviewId' element={ <StudentEditReview /> } />
          
          {/* student about course */}
          <Route path='student/course/:courseId/about-course' element={ <StudentAboutCourse /> } />
        </Route>

        {/* instructor role */}
        <Route element={<RequireAuth allowRoles={["INSTRUCTOR"]}/>}>
          <Route path='instructor' element={ <Instructor /> } />
          <Route path='instructor/create-course' element={ <CreateCourse/> } />
          <Route path='instructor/create-course/:id' element={ <UploadCourseMaterials/> } />
          <Route path='instructor/course/:courseId/assignment' element={ <InstructorAssignmentCourse /> } />
          <Route path='instructor/course/:courseId/assignment/chapter/:chapterIndex' element={ <InstructorAssignmentChapter /> } />
          <Route path='instructor/course/:courseId/assignment/chapter/:chapterIndex/answer/:noIndex' element={ <InstructorAssignmentAnswer /> } />
          <Route path='instructor/course/:courseId/post' element={ <InstructorPost /> } />
          <Route path='instructor/course/:courseId/post/:postId' element={ <InstructorPostComment /> } />
          <Route path='instructor/course/:courseId/review' element={ <InstructorReview /> } />

          <Route path='account-manage/instructor/bank' element={ <AccountManageInstructorBank/> } />
          <Route path='account-manage/instructor/transaction' element={ <AccountManageInstructorTransaction/> } />
        </Route>

        {/* admin role */}
        <Route element={<RequireAuth allowRoles={["ADMIN"]}/>}>
          <Route path='admin' element={ <Admin/> } />
          <Route path='admin/pending-course/:courseId' element={ <PendingCourse/> } />
        </Route>

      </Route>

    </Routes>
  );
}

export default App;