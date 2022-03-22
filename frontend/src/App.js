import MuiLogin from './components/MuiLogin';
import Register from './components/Register';
import Home from './components/Home';
import Student from './components/Student';
import Instructor from './components/Instructor';
import Admin from './components/Admin';
import Layout from './components/Layout';
import RequireAuth from './components/RequireAuth';

import { Routes, Route } from 'react-router-dom';
import Unauthorized from './components/Unauthorized';
function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        {/* public routes */}
        <Route path='/' element={<Home />} />
        <Route path='login' element={<MuiLogin />} />
        <Route path='register' element={<Register />} />
        <Route path='unauthorized' element={<Unauthorized />} />

        {/* private routes */}
        <Route element={<RequireAuth allowRoles={["STUDENT"]} />}>
          <Route path='student' element={<Student />} />
        </Route>
        <Route element={<RequireAuth allowRoles={["INSTRUCTOR"]} />}>
          <Route path='instructor' element={<Instructor />} />
        </Route>
        <Route element={<RequireAuth allowRoles={["ADMIN"]} />}>
          <Route path='admin' element={<Admin />} />
        </Route>
      </Route>

    </Routes>
  );
}

export default App;
