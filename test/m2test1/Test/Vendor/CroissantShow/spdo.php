<?php
/**
 * Created by PhpStorm.
 * User: Pierre
 * Date: 14/12/2016
 * Time: 14:53
 */

namespace CroissantShow\tests\units{
use mageekguy\atoum;


class spdo extends atoum\test
{

    public function userConnectionTest()
    {
        $this
            ->if(given($res = new \Mock\CroissantShow\spdo()))
            ->then
            ->boolean($res->userConnection('admin','admin'));
    }

    public function getInstanceTest()
    {
        $this
            ->if($res = new \CroissantShow\spdo())
            ->then
            ->true;
    }

    public function userInscriptionTest()
    {
        $this
            ->if($res = new \CroissantShow\spdo())
            ->then
            ->true;
    }

    public function isMailCorrectTest()
    {
        $this
            ->if($res = new \CroissantShow\spdo())
            ->then
            ->true;
    }

    public function afficheEventTest()
    {
        $this
            ->if($res = new \CroissantShow\spdo())
            ->then
            ->true;
    }

    public function participeTest()
    {
        $this
            ->if($res = new \CroissantShow\spdo())
            ->then
            ->true;
    }
}
}