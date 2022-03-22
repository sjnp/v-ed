import Login from './Login';
import MuiLogin from './MuiLogin';
import Register from './Register';
import Home from './Home';
import Student from './Student';
import Instructor from './Instructor';
import Admin from './Admin';
import Layout from './Layout';
import RequireAuth from './RequireAuth';

import { Routes, Route } from 'react-router-dom';
import Unauthorized from './Unauthorized';
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
