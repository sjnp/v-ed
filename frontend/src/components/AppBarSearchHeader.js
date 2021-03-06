import { useEffect, useState } from "react"
import { useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"

// component
import SignInSignUpModals from "./SignInSignUpModals"
import UserMenu from "./UserMenu"
import SearchBox from "./SearchBox"

// Material UI component
import AppBar from '@mui/material/AppBar'
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'

// custom hook
import useRefreshToken from "../hooks/useRefreshToken"

const AppBarSearchHeader = () => {

  const refresh = useRefreshToken();
  const navigate = useNavigate()

  const username = useSelector((state) => state.auth.value.username);
  const persist = useSelector((state) => state.auth.value.persist);

  const [ isLoading, setIsLoading ] = useState(true);

  useEffect(() => {
    let isMounted = true;
    const verifyRefreshToken = async () => {
      try {
        await refresh();
      } catch (err) {
        console.error(err);
      } finally {
        isMounted && setIsLoading(false);
      }
    }
    !username ? verifyRefreshToken() : setIsLoading(false);

    return () => isMounted = false;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <>
      <AppBar color="inherit" elevation={1}>
        <Container maxWidth="lg">
          <Toolbar>
            <Grid container alignItems='center' spacing={2}>
              <Grid item xs={3.5}>
                  <Typography variant="h6" color="text.primary" onClick={() => navigate('/')} sx={{ cursor: 'pointer' }}>
                    V-Ed
                  </Typography>
              </Grid>
              <Grid item xs={5}>
                <SearchBox />
              </Grid>
              <Grid item xs={3.5}>
              {
                !persist ? 
                  username ? 
                    <UserMenu />
                    : 
                    <SignInSignUpModals />
                  : 
                  isLoading ? 
                    <Grid container alignItems='center' justifyContent='flex-end'>
                      <p>Loading...</p>
                    </Grid>
                    : username ? 
                      <UserMenu />
                      :
                      <SignInSignUpModals />
              }
              </Grid>
            </Grid>
          </Toolbar>
        </Container>
      </AppBar>
      {/* Fixed Replacement see: https://mui.com/components/app-bar/#fixed-placement */}
      <Toolbar />
    </>
  )
}

export default AppBarSearchHeader;