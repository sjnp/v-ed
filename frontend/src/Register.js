import { useState, useEffect } from "react";
import axios from "./api/axios";

const USERNAME_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,24}$/;
const REGISTER_URL = '/api/users/register-new-student';

const Register = () => {

  const [username, setUsername] = useState('');
  const [validUsername, setValidUsername] = useState(false);

  const [password, setPassword] = useState('');
  const [validPassword, setValidPassword] = useState(false);

  const [matchPassword, setMatchPassword] = useState('');
  const [validMatch, setValidMatch] = useState(false);

  const [errMsg, setErrMsg] = useState('');
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    const result = USERNAME_REGEX.test(username);
    setValidUsername(result);
  }, [username])

  useEffect(() => {
    const result = PASSWORD_REGEX.test(password);
    setValidPassword(result);
    const match = password === matchPassword;
    setValidMatch(match);
  }, [password, matchPassword])

  useEffect(() => {
    setErrMsg('');
  }, [username, password, matchPassword])

  const handleSubmit = async (e) => {
    e.preventDefault();

    const v1 = USERNAME_REGEX.test(username);
    const v2 = PASSWORD_REGEX.test(password);
    if (!v1 || !v2) {
      setErrMsg("Invalid Entry");
      return;
    }
    try {
      const response = await axios.post(REGISTER_URL,
        JSON.stringify({ username, password }),
        {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true
        }
      );

      console.log(JSON.stringify(response?.data));

      setSuccess(true);
      setUsername('');
      setPassword('');
      setMatchPassword('');
    } catch (err) {
      if (!err?.response) {
        setErrMsg('No Server Response');
      } else if (err.response?.status === 409) {
        setErrMsg('Username Taken');
      } else {
        setErrMsg('Registration Failed')
      }
    }
  }
  return (
    <>
      {success ? (
        <section>
          <h1>Success!</h1>
          <p>
            <a href="#">Sign In</a>
          </p>
        </section>
      ) : (
        <section>
          <p
            className={errMsg ? "errmsg" : "offscreen"}
          >{errMsg}</p>
          <h1>Register</h1>
          <form onSubmit={handleSubmit}>
            <label htmlFor="username">
              Username:
              <span className={validUsername ? "valid" : "hide"}>
                Right!
              </span>
              <span className={validUsername || !username ? "hide" : "invalid"}>
                Wrong!
              </span>
            </label>
            <input
              type="email"
              id="username"
              autoComplete="off"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
            <p className={username && !validUsername ? "instructions" : "offscreen"}>
              Must be in email form.<br />
              ie. user@example.com
            </p>
            <label htmlFor="password">
              Password:
              <span className={validPassword ? "valid" : "hide"}>
                Right!
              </span>
              <span className={validPassword || !password ? "hide" : "invalid"}>
                Wrong!
              </span>
            </label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <p className={password && !validPassword ? "instructions" : "offscreen"}>
              Must include at least one uppercase letter, lowercase letter and number. <br />
              8 - 24 characters.
            </p>
            <label htmlFor="confirm_password">
              Confirm Password:
              <span className={validMatch && matchPassword ? "valid" : "hide"}>
                Right!
              </span>
              <span className={validMatch || !matchPassword ? "hide" : "invalid"}>
                Wrong!
              </span>
            </label>
            <input
              type="password"
              id="confirm_password"
              value={matchPassword}
              onChange={(e) => setMatchPassword(e.target.value)}
              required
            />
            <p className={!validMatch ? "instructions" : "offscreen"}>
              Password must be matched.
            </p>
            <button
              disabled={!validUsername || !validPassword || !validMatch ? true : false}
            >Sign Up</button>
          </form>
        </section>
      )}
    </>
  )
}

export default Register;