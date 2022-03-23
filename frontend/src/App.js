import MuiLogin from './components/MuiLogin';
import Register from './components/Register';
import Home from './pages/Home';
import Student from './pages/Student';
import Instructor from './pages/Instructor';
import Admin from './pages/Admin';
import RequireAuth from './components/RequireAuth';
import PersistLogin from './components/PersistLogin';

import { Routes, Route } from 'react-router-dom';
import Unauthorized from './pages/Unauthorized';
function App() {
  return (
    <Routes>
        {/* public routes */}
        <Route path='/' element={<Home />} />
        <Route path='login' element={<MuiLogin />} />
        <Route path='register' element={<Register />} />
        <Route path='unauthorized' element={<Unauthorized />} />

        {/* private routes */}
        <Route element={<PersistLogin />}>
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
