package com.trainee2.extras;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.trainee2.GUI.fragment.empregador.FragmentCadastroEmpregador;
import com.trainee2.GUI.fragment.empregador.FragmentLoginEmpregador;
import com.trainee2.GUI.fragment.estagiario.Home.FragmentNovaVaga;
import com.trainee2.GUI.fragment.estagiario.Home.FragmentVagaEmAberto;
import com.trainee2.GUI.fragment.estagiario.Home.FragmentVagaRecomendada;

public class MyFragmentPagerAdapterEMP extends FragmentStatePagerAdapter {
    private String[] mTabTitles;
    public MyFragmentPagerAdapterEMP(FragmentManager fm, String[] TabTitles) {
        super(fm);
        this.mTabTitles = TabTitles;

    }


    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new FragmentLoginEmpregador();
            case 1:
                return new FragmentCadastroEmpregador();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int i) {
        return this.mTabTitles[i];

    }
}
